package com.tobe.prediction.presentation.ui.predict.view

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.helper.Configs
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.Session
import com.tobe.prediction.model.predict.PredictService
import com.tobe.prediction.presentation.navigation.RouterHelper
import com.tobe.prediction.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class PredictSingleViewModel(private val predictService: PredictService, private val routerHelper: RouterHelper) : BaseViewModel(), KoinComponent {
    override val cd = CompositeDisposable()

    private val configs: Configs by inject()
    private var predictId: String? = null

    val errorMsg = ObservableField<String>()
    val predict = ObservableField<PredictDTO>()
    val progressLoad = ObservableBoolean(false)
    val isAuthor = ObservableBoolean(false)

    fun setPredictId(id: String?) {
        if (predictId == null) {
            predictId = id
            if (id != null) {
                loadPredictData(id)
            } else {
                errorMsg.set("No data") // todo use context
            }
        }
    }

    fun votePositive() {
        predictId?.let {
            predictService.saveVote(it, configs.optionPosIndex).subscribe { votes ->
                onVotesLoaded(votes)
            }
        }
    }

    fun voteNegative() {
        predictId?.let {
            predictService.saveVote(it, configs.optionNegIndex).subscribe { votes ->
                onVotesLoaded(votes)
            }
        }
    }

    fun onProfileClick() {
        predict.get()?.authorId?.let { routerHelper.navigateToProfile(it) }
    }

    private fun onVotesLoaded(votes: Pair<Int, Int>) {
        predict.get()?.apply {
            votePosCount = votes.first
            voteNegCount = votes.second
        }
        predict.notifyChange()
    }

    private fun loadPredictData(id: String) {
        progressLoad.set(true)
        predictService.loadPredictData(id)
                .subscribe({ dto ->
                    predict.set(dto)
                    val authorFlag = Session.user?.id == dto.authorId
                    if (isAuthor.get() == authorFlag) isAuthor.notifyChange() else isAuthor.set(authorFlag)
                    progressLoad.set(false)
                }, { th ->
                    errorMsg.set(th.message)
                    progressLoad.set(false)
                }).attachTo(cd)

        predict.get()
    }
}