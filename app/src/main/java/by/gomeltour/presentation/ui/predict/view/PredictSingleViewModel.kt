package by.gomeltour.presentation.ui.predict.view

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import by.gomeltour.domain.dto.PredictDTO
import by.gomeltour.helper.Configs
import by.gomeltour.helper.attachTo
import by.gomeltour.model.Session
import by.gomeltour.model.predict.PredictService
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
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