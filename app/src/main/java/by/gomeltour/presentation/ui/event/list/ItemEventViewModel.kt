package by.gomeltour.presentation.ui.event.list

import by.gomeltour.model.EventModel
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class ItemEventViewModel : BaseViewModel(), KoinComponent {
    override val cd = CompositeDisposable()

    private val routerHelper: RouterHelper by inject()

    var event: EventModel? = null

    fun onSelectItem() {
        event?.id?.let {
            routerHelper.navigateToEditSingle(it)
        }
    }
}