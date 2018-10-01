package com.tobe.prediction.presentation.ui.predict.view

/**
 * Created by Yahor_Fralou on 10/1/2018 12:51 PM.
 */
interface IPredictEditView {
    fun onSavedComplete()
    fun showSavingError()
    fun showSavingProgress(isShow: Boolean)
}