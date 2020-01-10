package by.gomeltour.presentation.ui.predict.view

import by.gomeltour.domain.dto.PredictDTO

interface IPredictSingleView {
    fun showDataError(message: String)

    fun displayMainInfo(dto: PredictDTO)
    fun showNoIdError()
}