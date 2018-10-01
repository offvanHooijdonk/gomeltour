package com.tobe.prediction.dao.impl

import com.google.firebase.firestore.CollectionReference
import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.domain.Predict
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 10/1/2018 2:21 PM.
 */

class PredictDao @Inject constructor(var refPredict: CollectionReference) : IPredictDao {

    override fun save(predict: Predict): Completable {
        val id = refPredict.document().id
        predict.id = id
        return RxFirestore.setDocument(refPredict.document(id), predict)
    }

    override fun list(): Maybe<MutableList<Predict>> {
        return RxFirestore.getCollection(refPredict, Predict::class.java)
    }
}