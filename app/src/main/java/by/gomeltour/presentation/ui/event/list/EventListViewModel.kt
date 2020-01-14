package by.gomeltour.presentation.ui.event.list

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import by.gomeltour.model.EventModel
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import by.gomeltour.presentation.ui.main.screenevents.ListScrollEvent
import by.gomeltour.presentation.ui.main.screenevents.ScreenEvent
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable

class EventListViewModel(//private val predictService: PredictService,
                         private val routerHelper: RouterHelper,
                         private val screenEvents: Observer<ScreenEvent>) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val eventsList = ObservableArrayList<EventModel>()
    val errorMsg = ObservableField<String>()
    val showEmpty = ObservableBoolean(false)
    val progressLoad = ObservableBoolean(false)

    private var isInit = false

    fun viewStart() {
        showEmpty.set(true) // FIXME temp, remove
        isInit = if (!isInit) {
            //loadPredicts()
            true
        } else isInit
    }

    fun updatePredicts() {
        //loadPredicts()
    }

    fun onListScroll(isDown: Boolean) {
        screenEvents.onNext(ListScrollEvent(isDown))
    }

    fun onActionButtonClick() {
        //routerHelper.navigateToPredictEdit()
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