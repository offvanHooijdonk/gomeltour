package com.tobe.prediction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tobe.prediction.domain.Vote
import io.reactivex.Completable

/**
 * Created by Yahor_Fralou on 10/1/2018 2:15 PM.
 */

@Dao
interface IVoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vote: Vote)
}