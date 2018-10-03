package com.tobe.prediction.presentation.presenter.predict.view

import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.domain.createVote
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.ui.predict.view.IPredictEditView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/27/2018 2:57 PM.
 */

class PredictEditPresenter @Inject constructor(var predictDao: IPredictDao, var voteDao: IVoteDao) {
    /*@Inject
    lateinit var predictDao: IPredictDao*/
    /*@Inject
    lateinit var voteDao: VoteDao*/

    private var view: IPredictEditView? = null
    private val cd = CompositeDisposable()

    fun attachView(view: IPredictEditView) {
        this.view = view
    }

    fun savePredict(predict: Predict, optionPicked: Int) {
        view?.showSavingProgress(true)
        /*Completable.merge(listOf(
                predictDao.save(predict),
                voteDao.save(vote)
        ))*/
        predictDao.save(predict)
                .mergeWith {
                    val vote = createVote(Session.user!!.id, predict.id, optionPicked)
                    voteDao.save(vote)
                }
                .subscribeOn(Schedulers.io()) // todo create transformers
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    view?.showSavingProgress(false)
                    view?.showSavingError()
                }
                .subscribe {
                    view?.showSavingProgress(false)
                    view?.onSavedComplete()
                }.attachTo(cd)
    }

    fun detachView() {
        cd.clear()
        view = null
    }
}