package by.gomeltour.presentation.ui.predict.list

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import by.gomeltour.app.App
import by.gomeltour.domain.dto.PredictDTO
import by.gomeltour.helper.attachTo
import by.gomeltour.model.predict.PredictService
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import by.gomeltour.presentation.ui.main.screenevents.ListScrollEvent
import by.gomeltour.presentation.ui.main.screenevents.ScreenEvent
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable

class PredictListViewModel(private val predictService: PredictService,
                           private val routerHelper: RouterHelper,
                           private val screenEvents: Observer<ScreenEvent>) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val predictsList = ObservableArrayList<PredictDTO>()
    val errorMsg = ObservableField<String>()
    val showEmpty = ObservableBoolean(false)
    val progressLoad = ObservableBoolean(false)

    private var isInit = false

    fun viewStart() {
        isInit = if (!isInit) {
            loadPredicts()
            true
        } else isInit
    }

    fun updatePredicts() {
        loadPredicts()
    }

    fun onListScroll(isDown: Boolean) {
        screenEvents.onNext(ListScrollEvent(isDown))
    }

    fun onActionButtonClick() {
        routerHelper.navigateToPredictEdit()
    }

    private fun loadPredicts() {
        progressLoad.set(true)
        predictService.getPredicts()
                .subscribe({
                    predictsList.apply {
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
    }
}