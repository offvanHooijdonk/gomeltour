package com.tobe.prediction.dao

import androidx.room.*
import com.tobe.prediction.domain.Vote
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * Created by Yahor_Fralou on 10/1/2018 2:15 PM.
 */

@Dao
interface VoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vote: Vote)

    @Query("select count(*) from Votes where predict = :predictId and option = :option")
    fun getVotesCount(predictId: String, option: Int): Maybe<Int>

    @Query("delete from Votes where predict = :predictId and user = :userId")
    fun removeVote(predictId: String, userId: String)
}