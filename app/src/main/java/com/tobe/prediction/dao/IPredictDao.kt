package com.tobe.prediction.dao

import com.tobe.prediction.domain.Predict
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * Created by Yahor_Fralou on 10/1/2018 2:15 PM.
 */
interface IPredictDao {
    fun save(predict: Predict): Completable
    fun list(): Maybe<MutableList<Predict>>
}