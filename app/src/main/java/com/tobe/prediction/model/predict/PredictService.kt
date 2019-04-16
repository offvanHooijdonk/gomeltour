package com.tobe.prediction.model.predict

import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.domain.dto.PredictDTO
import com.tobe.prediction.domain.dto.convertToPredictDTO
import com.tobe.prediction.helper.schedulersIO
import io.reactivex.Single

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

}