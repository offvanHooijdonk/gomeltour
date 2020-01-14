package by.gomeltour.service.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import by.gomeltour.model.UserModel
import by.gomeltour.model.createUser
import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject

/**
 * Created by Yahor_Fralou on 9/26/2018 5:57 PM.
 */

class AuthFirebase  {
    fun signIn(credential: AuthCredential): Maybe<UserModel> {
        val subj = PublishSubject.create<UserModel>()
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            val user = it.result?.user
            if (user != null) {
                subj.onNext(createUser(user.uid))
            } else {
                subj.onError(Exception("No result while signing in with Firebase"))
            }
        }.addOnFailureListener {
            subj.onError(it)
        }
        return subj.firstElement()
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}