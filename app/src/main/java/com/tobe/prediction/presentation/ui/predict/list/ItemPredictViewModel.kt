package com.tobe.prediction.presentation.ui.predict.list

import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.presentation.navigation.RouterHelper
import com.tobe.prediction.presentation.ui.BaseViewModel
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