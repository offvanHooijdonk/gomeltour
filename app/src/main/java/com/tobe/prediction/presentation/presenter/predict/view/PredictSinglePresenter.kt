package com.tobe.prediction.presentation.presenter.predict.view

import android.content.Context
import com.tobe.prediction.R
import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.domain.dto.convertToPredictDTO
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.helper.schedulersIO
import com.tobe.prediction.presentation.ui.predict.view.IPredictSingleView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PredictSinglePresenter @Inject constructor(private val ctx: Context, private val predictDao: IPredictDao, private val userDao: IUserDao, private val voteDao: IVoteDao) {

    private var view: IPredictSingleView? = null
    private val cd = CompositeDisposable()

    fun attachView(v: IPredictSingleView) {
        this.view = v
    }

    fun onViewStarted(predictId: String?) {
        if (predictId == null) {
            // todo show some error
            view?.showNoIdError()
            return
        }
        val optionPos = ctx.resources.getInteger(R.integer.option_positive)
        val optionNeg = ctx.resources.getInteger(R.integer.option_negative)

        predictDao.getById(predictId)
                .flatMap { predict -> userDao.getById(predict.userId).map { user -> convertToPredictDTO(predict, user) } }
                .flatMap { dto -> voteDao.getVotesCount(dto.id, optionPos).map { count -> dto.apply { votePosCount = count } } }
                .flatMap { dto -> voteDao.getVotesCount(dto.id, optionNeg).map { count -> dto.apply { voteNegCount = count } } }
                .schedulersIO()
                .subscribe({
                    view?.displayMainInfo(it)
                }, {
                    view?.showDataError(it.message ?: it.toString())
                }).attachTo(cd)
    }

    fun detachView() {
        cd.clear()
        view = null
    }
}