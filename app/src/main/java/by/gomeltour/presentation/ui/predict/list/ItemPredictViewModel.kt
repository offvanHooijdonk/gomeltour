package by.gomeltour.presentation.ui.predict.list

import by.gomeltour.domain.dto.PredictDTO
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class ItemPredictViewModel : BaseViewModel(), KoinComponent {
    override val cd = CompositeDisposable()

    private val routerHelper: RouterHelper by inject()

    var predict: PredictDTO? = null

    fun onSelectItem() {
        predict?.id?.let {
            routerHelper.navigateToPredictSingle(it)
        }
    }
}