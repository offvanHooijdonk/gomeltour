package com.tobe.prediction.presentation.presenter.predict.list

import android.util.Log
import com.tobe.prediction.app.App
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.predict.PredictInteractor
import com.tobe.prediction.presentation.ui.predict.list.IPredictListView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/27/2018 2:54 PM.
 */

class PredictListPresenter @Inject constructor(/*var predictDao: IPredictDao, var userDao: IUserDao*/ private var predictInteractor: PredictInteractor) {
    private var view: IPredictListView? = null
    private val cd = CompositeDisposable()

    fun attachView(view: IPredictListView) {
        this.view = view
    }

    fun loadPredictList() {
        predictInteractor.getPredicts()
                .subscribe(
                        { list -> view?.onDataLoaded(list) },
                        { th -> Log.e(App.TAG, "Error loading predicts", th); view?.showError(th) } // fixme do not pass Throwable to UI layer
                ).attachTo(cd)
    }

    fun detachView() {
        cd.clear()
        view = null
    }
}