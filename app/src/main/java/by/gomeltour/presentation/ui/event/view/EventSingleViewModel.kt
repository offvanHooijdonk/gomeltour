package by.gomeltour.presentation.ui.event.view

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import by.gomeltour.helper.Configs
import by.gomeltour.model.EventModel
import by.gomeltour.service.Session
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class EventSingleViewModel(/*private val predictService: PredictService,*/ private val routerHelper: RouterHelper) : BaseViewModel(), KoinComponent {
    override val cd = CompositeDisposable()

    private val configs: Configs by inject()
    private var predictId: String? = null

    val errorMsg = ObservableField<String>()
    val event = ObservableField<EventModel>()
    val progressLoad = ObservableBoolean(false)
    val isAuthor = ObservableBoolean(false)

    fun setPredictId(id: String?) {
        if (predictId == null) {
            predictId = id
            if (id != null) {
                //loadPredictData(id)
            } else {
                errorMsg.set("No data") // todo use context
            }
        }
    }

    fun onProfileClick() {
        //event.get()?.authorId?.let { routerHelper.navigateToProfile(it) }
    }

    /*private fun loadPredictData(id: String) {
        progressLoad.set(true)
        predictService.loadPredictData(id)
                .subscribe({ dto ->
                    event.set(dto)
                    val authorFlag = Session.user?.id == dto.authorId
                    if (isAuthor.get() == authorFlag) isAuthor.notifyChange() else isAuthor.set(authorFlag)
                    progressLoad.set(false)
                }, { th ->
                    errorMsg.set(th.message)
                    progressLoad.set(false)
                }).attachTo(cd)

        event.get()
    }*/
}