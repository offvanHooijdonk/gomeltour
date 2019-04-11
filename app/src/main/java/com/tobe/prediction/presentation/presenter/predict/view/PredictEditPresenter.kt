package com.tobe.prediction.presentation.presenter.predict.view

import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.domain.Vote
import com.tobe.prediction.domain.createVote
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.ui.predict.view.IPredictEditView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yahor_Fralou on 9/27/2018 2:57 PM.
 */

class PredictEditPresenter(private val predictDao: IPredictDao, private val voteDao: IVoteDao) {

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
        setUpId(predict)
        Completable.fromAction { predictDao.save(predict) }
                .mergeWith(
                        Completable.fromAction { voteDao.save(setUpId(createVote(Session.user!!.id, predict.id, optionPicked))) }
                )
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

    @Deprecated("Need to set at server")
    private fun setUpId(predict: Predict) {
        predict.id = System.currentTimeMillis().toString()
    }

    @Deprecated("Need to set at server")
    private fun setUpId(vote: Vote) = vote.apply { this.id = "${this.predict}+${this.user}" }
}