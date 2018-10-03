package com.tobe.prediction.presentation.presenter.predict.list

import android.util.Log
import com.tobe.prediction.app.App
import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.domain.dto.convertToPredictDTO
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.presentation.ui.predict.list.IPredictListView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/27/2018 2:54 PM.
 */

class PredictListPresenter @Inject constructor(var predictDao: IPredictDao, var userDao: IUserDao) {
    private var view: IPredictListView? = null
    private val cd = CompositeDisposable()

    fun attachView(view: IPredictListView) {
        this.view = view
    }

    fun loadPredictList() {
        predictDao.list()
                .flattenAsObservable { list -> list }
                .flatMap { predict ->
                    userDao.getById(predict.userId)
                            .toObservable()
                            .map { user -> convertToPredictDTO(predict, user) }
                }
                .collect({ mutableListOf<PredictDTO>() }, { list, dto -> list.add(dto) })
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