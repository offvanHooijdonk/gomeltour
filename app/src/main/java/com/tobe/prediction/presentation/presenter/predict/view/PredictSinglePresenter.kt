package com.tobe.prediction.presentation.presenter.predict.view

import com.tobe.prediction.presentation.ui.predict.view.IPredictSingleView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PredictSinglePresenter @Inject constructor() {
    private var view: IPredictSingleView? = null
    private val cd = CompositeDisposable()

    fun attachView(v: IPredictSingleView) {
        this.view = v
    }

    fun detachView() {
        cd.clear()
        view = null
    }
}