package by.gomeltour.presentation.ui.preferences

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import by.gomeltour.R
import by.gomeltour.helper.gone
import by.gomeltour.helper.invisible
import by.gomeltour.helper.visible
import by.gomeltour.model.AchievementModel
import by.gomeltour.model.EventModel
import by.gomeltour.model.LocationModel
import by.gomeltour.repository.AchievementsRepo
import by.gomeltour.repository.EventRepo
import by.gomeltour.repository.LocationRepo
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.pref_init_data.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.reflect.Type
import java.util.*

class InitDataPreference(private val ctx: Context, attrs: AttributeSet) : Preference(ctx, attrs), KoinComponent {
    private val eventRepo: EventRepo by inject()
    private val locationRepo: LocationRepo by inject()
    private val achievementsRepo: AchievementsRepo by inject()

    init {
        layoutResource = R.layout.pref_init_data
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)

        holder?.apply {
            itemView.setOnClickListener { loadInitData(itemView) }
        }
    }

    private fun loadInitData(view: View) {
        showProgress(true, view)

        try {
            val events = GsonBuilder()
                    .registerTypeAdapter(Date::class.java, DateDeserializer)
                    .registerTypeAdapter(Date::class.java, DateSerializer)
                    .create().fromJson<Array<EventModel>>(ctx.resources.openRawResource(R.raw.initdata).reader(), TypeToken.getArray(EventModel::class.java).type)

            val locations = GsonBuilder().create()
                    .fromJson<Array<LocationModel>>(ctx.resources.openRawResource(R.raw.locations).reader(), TypeToken.getArray(LocationModel::class.java).type)
            val achievements = GsonBuilder().create()
                    .fromJson<Array<AchievementModel>>(ctx.resources.openRawResource(R.raw.achievements).reader(), TypeToken.getArray(AchievementModel::class.java).type)

            eventRepo.upsertBatch(listOf(*events))
                    .zip(locationRepo.upsertBatch(listOf(*locations))) { _, _ -> }
                    .zip(achievementsRepo.upsertBatch(listOf(*achievements))) { _, _ -> }
                    .onCompletion {
                        view.img_done.visible();
                        showProgress(false, view)
                    }
                    .launchIn(CoroutineScope(Dispatchers.Main))

        } catch (e: Exception) {
            Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG).show()
            Log.e("g-tour", "Error saving init data", e)
            showProgress(false, view)
        }
    }

    private fun showProgress(isShow: Boolean, view: View) {
        with(view) {
            /*root.*/isEnabled = !isShow
            if (isShow) progress_init_data.visible() else progress_init_data.gone()
            if (isShow) img_done.invisible()
        }
    }
}

private object DateDeserializer : JsonDeserializer<Date> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? =
            json?.asJsonPrimitive?.let { Date(it.asLong) }
}

private object DateSerializer : JsonSerializer<Date> {
    override fun serialize(date: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            date?.let { JsonPrimitive(date.time) } ?: JsonNull.INSTANCE

}