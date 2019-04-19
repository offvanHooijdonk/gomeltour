package com.tobe.prediction.model.predict

import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.domain.Predict
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.domain.dto.convertToPredictDTO
import com.tobe.prediction.helper.schedulersIO
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yahor_Fralou on 10/31/2018 2:09 PM.
 */

class PredictService constructor(private var predictDao: IPredictDao, private var userDao: IUserDao/*, var voteDao: IVoteDao*/) {
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
                    /*.mergeWith(
                            Completable.fromAction { voteDao.save(setUpId(createVote(Session.user!!.id, predict.predictId, optionId))) }
                    )*/
                    .subscribeOn(Schedulers.io()) // todo create transformers
                    .observeOn(AndroidSchedulers.mainThread())

}