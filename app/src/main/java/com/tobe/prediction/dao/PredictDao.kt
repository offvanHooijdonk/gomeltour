package com.tobe.prediction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tobe.prediction.domain.Predict
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * Created by Yahor_Fralou on 10/1/2018 2:15 PM.
 */

@Dao
interface PredictDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(predict: Predict)

    @Query("select * from Predicts")
    fun list(): Maybe<MutableList<Predict>>

    @Query("select * from Predicts where predictId = :id")
    fun getById(id: String): Maybe<Predict>
}