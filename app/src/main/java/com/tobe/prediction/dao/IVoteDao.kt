package com.tobe.prediction.dao

import com.tobe.prediction.domain.Vote
import io.reactivex.Completable

/**
 * Created by Yahor_Fralou on 10/1/2018 2:15 PM.
 */

interface IVoteDao {
    fun save(vote: Vote): Completable
}