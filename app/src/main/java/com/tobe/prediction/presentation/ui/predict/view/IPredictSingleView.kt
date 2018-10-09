package com.tobe.prediction.presentation.ui.predict.view

import com.tobe.prediction.domain.dto.PredictDTO

interface IPredictSingleView {
    fun showDataError(message: String)

    fun displayMainInfo(dto: PredictDTO)
    fun showNoIdError()
}