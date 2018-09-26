package com.tobe.prediction.model.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseAuth
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/26/2018 5:57 PM.
 */

class AuthFirebase @Inject constructor() {
    fun signIn(credential: AuthCredential): Maybe<AuthResult> =
            RxFirebaseAuth.signInWithCredential(FirebaseAuth.getInstance(), credential)

}