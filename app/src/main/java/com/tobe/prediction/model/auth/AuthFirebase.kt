package com.tobe.prediction.model.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.domain.createUser
import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/26/2018 5:57 PM.
 */

class AuthFirebase @Inject constructor() {
    fun signIn(credential: AuthCredential): Maybe<UserBean> {
        val subj = PublishSubject.create<UserBean>()
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            it.result
            subj.onNext(createUser(it.result.user.uid))
        }.addOnFailureListener {
            subj.onError(it)
        }
        return subj.firstElement()
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}