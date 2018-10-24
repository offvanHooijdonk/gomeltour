package com.tobe.prediction.model.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.domain.createUser
import durdinapps.rxfirebase2.RxFirebaseAuth
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/26/2018 5:57 PM.
 */

class AuthFirebase @Inject constructor() {
    fun signIn(credential: AuthCredential): Maybe<UserBean> =
            RxFirebaseAuth.signInWithCredential(FirebaseAuth.getInstance(), credential)
                    .map { result -> createUser(id = result.user.uid) } // todo remove RxFirebase lib usage as it is used only here

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}