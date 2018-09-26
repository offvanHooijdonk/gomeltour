package com.tobe.prediction.model.auth

import android.arch.persistence.room.EmptyResultSetException
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tobe.prediction.R
import com.tobe.prediction.dao.UserDao
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.model.Session
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/18/2018 12:48 PM.
 */

class AuthGoogle @Inject constructor() {
    @Inject
    lateinit var ctx: Context
    @Inject
    lateinit var userDao: UserDao

    fun getLoggedUser(): Maybe<UserBean> {
        val signedUser = FirebaseAuth.getInstance().currentUser
        //val account = GoogleSignIn.getLastSignedInAccount(ctx)
        return if (signedUser == null) {
            Maybe.empty()
        } else {
            getUser(signedUser.uid)
                    .doOnSuccess { user ->  Session.user = user }
        }
    }

    // todo make this authenticator more generic, move google part to another class
    fun signInUser(signInData: Intent): Observable<UserBean> {
        val task = GoogleSignIn.getSignedInAccountFromIntent(signInData)

        return try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            signInWithFirebase(credential, account)
                    .observeOn(Schedulers.io())
                    .doOnNext { user ->  userDao.save(user) }
                    .doOnNext { user ->  Session.user = user}
        } catch (e: ApiException) {
            Observable.error(e)
        }
    }

    fun getSignInClient(): GoogleSignInClient = GoogleSignIn.getClient(ctx, prepareOptions())

    // todo create FirebaseAuthenticator
    private fun signInWithFirebase(credential: AuthCredential, account: GoogleSignInAccount): Observable<UserBean> {
        val publisher = PublishSubject.create<UserBean>()

        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = UserBean(
                        id = FirebaseAuth.getInstance().currentUser!!.uid,
                        accountKey = account.id!!,
                        name = account.displayName
                        ?: account.id!!) // todo create user with special class
                publisher.onNext(user)
                publisher.onComplete()
            } else {
                publisher.onError(Exception("User Firebase Sign In failed!")) // todo create a dedicated exception?
            }
        }

        return publisher
    }

    private fun getUser(id: String): Maybe<UserBean> = userDao.getById(id)
            .toMaybe()
            .onErrorResumeNext { th: Throwable ->
                if (th is EmptyResultSetException)
                    Maybe.empty<UserBean>()
                else
                    Maybe.error<UserBean>(th)
            }


    private fun prepareOptions(): GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(ctx.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
}