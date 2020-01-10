package by.gomeltour.model.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import by.gomeltour.domain.UserBean
import by.gomeltour.domain.createUser
import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject

/**
 * Created by Yahor_Fralou on 9/26/2018 5:57 PM.
 */

class AuthFirebase  {
    fun signIn(credential: AuthCredential): Maybe<UserBean> {
        val subj = PublishSubject.create<UserBean>()
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            val result = it.result
            if (result != null) {
                subj.onNext(createUser(result.user.uid))
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