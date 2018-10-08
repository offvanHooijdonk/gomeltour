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

class VoteDao @Inject constructor(var refPredict: CollectionReference) : IVoteDao {
    override fun save(vote: Vote): Completable {
        /*val id = refPredict.document().id
        vote.id = id*/
        return RxFirestore.addDocument(
                refPredict
                        .document(vote.predict)
                        .collection(COLL_OPTIONS)
                        .document(vote.option.toString())
                        .collection(COLL_VOTES)/*.document()*/
                , vote).ignoreElement()
    }
}