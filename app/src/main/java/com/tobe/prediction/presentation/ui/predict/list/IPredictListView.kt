package com.tobe.prediction.presentation.ui.predict.list

import com.tobe.prediction.domain.dto.PredictDTO

/**
 * Created by Yahor_Fralou on 9/21/2018 4:37 PM.
 */
interface IPredictListView {
    fun onDataLoaded(list: List<PredictDTO>)
    fun showError(th: Throwable?)
}