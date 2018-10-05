package com.tobe.prediction.dao.impl

import com.google.firebase.firestore.CollectionReference
import com.tobe.prediction.dao.IPredictDao
import com.tobe.prediction.domain.Predict
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Maybe
import java.util.*
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 10/1/2018 2:21 PM.
 */

class PredictDao @Inject constructor(var refPredict: CollectionReference) : IPredictDao {

    override fun save(predict: Predict): Completable {
        val id = refPredict.document().id
        predict.id = id
        val predictDoc = PredictDoc(predict.id, predict.title, predict.text, predict.dateOpenTill, predict.dateFulfillment, predict.isActive, predict.userId)
        return RxFirestore.setDocument(refPredict.document(id), predictDoc) // todo try addDocument
                .mergeWith(
                        RxFirestore.setDocument(refPredict.document(id).collection("options").document("0"), OptionDoc(0, predict.options[0]))
                )
                .mergeWith(
                        RxFirestore.setDocument(refPredict.document(id).collection("options").document("1"), OptionDoc(1, predict.options[1]))
                )
    }

    override fun list(): Maybe<MutableList<Predict>> {
        return RxFirestore.getCollection(refPredict, Predict::class.java)
    }

    private data class PredictDoc(var id: String = "",
                                  var title: String = "",
                                  var text: String = "",
                                  var dateOpenTill: Date = Date(),
                                  var dateFulfillment: Date = Date(),
                                  var isActive: Boolean = true,
                                  var userId: String = "") // todo reference

    private data class OptionDoc(var code: Int = -1, var text: String = "")
}