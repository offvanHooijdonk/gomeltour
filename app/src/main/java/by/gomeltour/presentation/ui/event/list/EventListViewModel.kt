package by.gomeltour.presentation.ui.event.list

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import by.gomeltour.R
import by.gomeltour.app.LOGCAT
import by.gomeltour.model.EventModel
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import by.gomeltour.presentation.ui.main.screenevents.ListScrollEvent
import by.gomeltour.presentation.ui.main.screenevents.ScreenEvent
import by.gomeltour.service.event.EventService
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.*

class EventListViewModel(private val eventService: EventService,
                         private val routerHelper: RouterHelper,
                         private val screenEvents: Observer<ScreenEvent>) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val eventsList = ObservableArrayList<EventModel>()
    val errorMsg = ObservableInt()
    val showEmpty = ObservableBoolean(true)
    val progressLoad = ObservableBoolean(false)


    init {
        loadEvents()
    }

    fun updatePredicts() {
        loadEvents()
    }

    fun onListScroll(isDown: Boolean) {
        screenEvents.onNext(ListScrollEvent(isDown))
    }

    fun onActionButtonClick() {
        //routerHelper.navigateToPredictEdit()
    }

    private fun loadEvents() {
        eventService.getEventsForPeriod(0, System.currentTimeMillis())
                .onStart { progressLoad.set(true) }
                .onEach {
                    eventsList.apply { clear(); addAll(it) }
                    showEmpty.set(it.isEmpty())
                }
                .catch {
                    errorMsg.set(R.string.error_loading)
                    Log.e(LOGCAT, "ERROR", it)
                }
                .onCompletion { progressLoad.set(false) }
                .launchIn(viewModelScope)
    }

    /*private fun loadPredicts() {
        progressLoad.set(true)
        predictService.getPredicts()
                .subscribe({
                    eventsList.apply {
                        clear(); addAll(it)
                        progressLoad.set(false)
                        showEmpty.set(it.isEmpty())
                    }
                }, { th ->
                    Log.e(App.LOGCAT, "Error loading predicts", th)
                    progressLoad.set(false)
                    errorMsg.set(th.message)
                } // todo apply better handling
                ).attachTo(cd)
    }*/
}