package by.gomeltour.presentation.ui

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.gomeltour.R
import by.gomeltour.helper.*
import by.gomeltour.model.AchievementModel
import by.gomeltour.model.CheckInModel
import by.gomeltour.model.EventModel
import by.gomeltour.model.LocationModel
import by.gomeltour.presentation.formatDegrees
import by.gomeltour.presentation.formatDistance
import by.gomeltour.presentation.ui.achievements.list.AchievementsAdapter
import by.gomeltour.presentation.ui.achievements.list.LocationsAdapter
import by.gomeltour.presentation.ui.achievements.location.view.CheckInAdapter
import by.gomeltour.presentation.ui.event.list.EventAdapter
import by.gomeltour.service.loadAppBarUserPhoto
import by.gomeltour.service.loadImageRounded
import by.gomeltour.service.loadPoster
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import org.jetbrains.anko.design.longSnackbar
import java.util.*

@BindingAdapter("errorMsg")
fun setErrorMessage(textView: TextView, message: String?) {
    textView.visibility = if (message != null) View.VISIBLE else View.GONE
    textView.text = message
}

@BindingAdapter("errorMsg")
fun setErrorSnackbar(/*coordinatorLayout: CoordinatorLayout,*/view: View, stringRes: Int?) {
    stringRes?.let {
        if (stringRes != 0) {
            /*coordinatorLayout*/view.longSnackbar(it).colorError()
        }
    }
}

@BindingAdapter("invalidMsg")
fun setWarnSnackbar(/*coordinatorLayout: CoordinatorLayout,*/view: View, invalidMessage: String?) {
    invalidMessage?.let {
        if (it.isNotEmpty()) {
            /*coordinatorLayout*/view.longSnackbar(it).colorWarn()
        }
    }
}

@BindingAdapter("enabled")
fun setEnabled(view: View, enabled: Boolean) {
    view.isEnabled = enabled
}

@BindingAdapter("visible")
fun setVisibleOrGone(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("invisible")
fun setVisibleOrInvisible(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("refreshing")
fun setRefreshing(refreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
    refreshLayout.isRefreshing = refreshing
}

@BindingAdapter("eventsList")
fun setEventsList(recyclerView: RecyclerView, list: List<EventModel>) {
    (recyclerView.adapter as? EventAdapter)?.update(list)
}

@BindingAdapter("locationsList")
fun setLocationsList(recyclerView: RecyclerView, list: List<LocationModel>) {
    (recyclerView.adapter as? LocationsAdapter)?.update(list)
}

@BindingAdapter("achievementsList")
fun setAchievementsList(recyclerView: RecyclerView, list: PagedList<AchievementModel>?) {
    (recyclerView.adapter as? AchievementsAdapter)?.submitList(list)
}

@BindingAdapter("checksList")
fun setChecksInList(recyclerView: RecyclerView, list: List<CheckInModel>) {
    (recyclerView.adapter as? CheckInAdapter)?.update(list)
}

@BindingAdapter("numText")
fun setNumberText(textView: TextView, number: Int) {
    textView.text = number.toString() // todo implement numbers formatting as 28.4k
}

@BindingAdapter("timeShort")
fun setTimeShort(textView: TextView, date: Date?) {
    if (date == null) {
        textView.gone()
    } else {
        textView.text = DateFormat.getTimeFormat(textView.context).format(date)
        textView.visible()
    }
}

@BindingAdapter("dateShort")
fun setDateShort(textView: TextView, date: Date?) {
    if (date == null) {
        textView.gone()
    } else {
        val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "dd MMMM")
        textView.text = DateFormat.format(pattern, date)
        textView.visible()
    }
}

@BindingAdapter("dateLong")
fun setDateLong(textView: TextView, date: Date?) {
    if (date == null) {
        textView.gone()
    } else {
        textView.visible()
        textView.text = DateFormat.getLongDateFormat(textView.context).format(date)
    }
}

@BindingAdapter("timestamp")
fun setTimestamp(textView: TextView, date: Date) {
    textView.text = DateFormat.getLongDateFormat(textView.context).format(date) + " " + DateFormat.getTimeFormat(textView.context).format(date) // todo fix it
}

@BindingAdapter("extendState")
fun setExtendState(efab: ExtendedFloatingActionButton, isExtend: Boolean) {
    if (isExtend xor efab.isExtended) {
        if (isExtend) efab.extend() else efab.shrink()
    }
}

@BindingAdapter("visible")
fun setEFABVisibility(efab: ExtendedFloatingActionButton, isShow: Boolean) {
    if (isShow xor efab.isShown) {
        if (isShow) efab.show() else efab.hide()
    }
}

@BindingAdapter("avatarUrl")
fun setAvatarUrl(imageView: ImageView, url: String?) {
    imageView.loadImageRounded(url, R.drawable.ic_person_default_24)
}

@BindingAdapter("locationImageUrl")
fun setLocationImageUrl(imageView: ImageView, url: String?) {
    imageView.loadImageRounded(url, R.drawable.ic_placeholder_place_24)
}

@BindingAdapter("achievementImageUrl")
fun setAchievementImageUrl(imageView: ImageView, url: String?) {
    imageView.loadImageRounded(url, R.drawable.ic_placeholder_achievement_24)
}

@BindingAdapter("posterUrl")
fun setPosterUrl(imageView: ImageView, url: String?) {
    imageView.loadPoster(url)
}

@BindingAdapter("appBarAvatarUrl")
fun setAppBarAvatarUrl(imageView: ImageView, url: String?) {
    if (url == null) imageView.setImageResource(R.drawable.ic_user_white_24) else imageView.loadAppBarUserPhoto(url)
}

@BindingAdapter("placeName")
fun setPlaceName(textView: TextView, placeName: String?) {
    if (placeName != null && placeName.isNotBlank()) {
        textView.text = placeName
    } else {
        textView.text = textView.context.getString(R.string.place_none)
    }
}

@BindingAdapter("latLng")
fun setLocationLatLng(textView: TextView, location: LatLng?) {
    location?.let {
        val latString = formatDegrees(textView.context, location.latitude)
        val lngString = formatDegrees(textView.context, location.longitude)

        textView.text = textView.context.getString(R.string.latlng_format, latString, lngString)
    }
}

@BindingAdapter("earned")
fun setImageEarned(img: ImageView, earned: Boolean) {
    img.alpha = if (earned) 1.0f else 0.5f
}

@BindingAdapter("earned")
fun setEarnedText(textView: TextView, earned: Boolean) {
    textView.alpha = if (earned) 1.0f else 0.7f
}

@BindingAdapter("distance")
fun setDistanceText(textView: TextView, distance: Float?) {
    textView.text = if (distance != null) formatDistance(textView.context, distance.toInt()) else ""
}