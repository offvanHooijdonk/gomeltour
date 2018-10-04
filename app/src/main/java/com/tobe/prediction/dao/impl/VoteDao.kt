package com.tobe.prediction.dao.impl

import com.google.firebase.firestore.CollectionReference
import com.tobe.prediction.dao.IVoteDao
import com.tobe.prediction.domain.Vote
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 10/1/2018 2:21 PM.
 */

class VoteDao @Inject constructor(var refVote: CollectionReference) : IVoteDao {
    override fun save(vote: Vote): Completable {
        val id = refVote.document().id
        vote.id = id
        return RxFirestore.setDocument(refVote.document(id), vote)
    }
}