package by.gomeltour.model.predict

import by.gomeltour.dao.PredictDao
import by.gomeltour.dao.UserDao
import by.gomeltour.dao.VoteDao
import by.gomeltour.domain.Predict
import by.gomeltour.domain.Vote
import by.gomeltour.domain.createVote
import by.gomeltour.domain.dto.PredictDTO
import by.gomeltour.domain.dto.convertToPredictDTO
import by.gomeltour.helper.Configs
import by.gomeltour.helper.schedulersIO
import by.gomeltour.model.Session
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Yahor_Fralou on 10/31/2018 2:09 PM.
 */

class PredictService constructor(private var predictDao: PredictDao, private var userDao: UserDao, private var voteDao: VoteDao) : KoinComponent {
    private val configs: Configs by inject()
    // todo implement Observables for the data

    fun getPredicts(): Single<out List<PredictDTO>> {
        return predictDao.list()
                .flattenAsObservable { list -> list }
                .flatMap { predict ->
                    userDao.getById(predict.userId)
                            .toObservable()
                            .map { user -> convertToPredictDTO(predict, user) }
                }
                .schedulersIO()
                .collect({ mutableListOf<PredictDTO>() }, { list, dto -> list.add(dto) })
    }

    fun savePredict(predict: Predict, optionSelected: Int): Completable =
            Completable.fromAction { predictDao.save(predict) }
                    .mergeWith(
                            Completable.fromAction { voteSync(predict.predictId, optionSelected) }
                    )
                    .schedulersIO()

    fun saveVote(predictId: String, option: Int): Maybe<Pair<Int, Int>> = // todo return new votes numbers
            Completable.fromAction {
                voteDao.removeVote(predictId, Session.user!!.id)
                voteSync(predictId, option)
            }.andThen(
                    /*.toMaybe<Unit>()
                    .flatMap {*/
                        voteDao.getVotesCount(predictId, configs.optionPosIndex)
                    /*}*/.flatMap { pos ->
                        voteDao.getVotesCount(predictId, configs.optionNegIndex).map { neg ->
                            pos to neg
                        }
                    })
                    .schedulersIO()

    private fun voteSync(predictId: String, option: Int) {
        voteDao.save(setUpId(createVote(Session.user!!.id, predictId, option)))
    }

    private fun setUpId(vote: Vote): Vote = vote.apply { id = "$predict|$user" }

    fun loadPredictData(predictId: String) =
            predictDao.getById(predictId)
                    .flatMap { predict -> userDao.getById(predict.userId).map { user -> convertToPredictDTO(predict, user) } }
                    .flatMap { dto -> voteDao.getVotesCount(dto.id, configs.optionPosIndex).map { count -> dto.apply { votePosCount = count } } }
                    .flatMap { dto -> voteDao.getVotesCount(dto.id, configs.optionNegIndex).map { count -> dto.apply { voteNegCount = count } } }
                    .schedulersIO()

}