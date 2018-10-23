package com.tobe.prediction.model.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tobe.prediction.R
import com.tobe.prediction.dao.IUserDao
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.model.Session
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/18/2018 12:48 PM.
 */

class AuthGoogle @Inject constructor() {
    @Inject
    lateinit var ctx: Context
    @Inject
    lateinit var userDao: IUserDao
    @Inject
    lateinit var authFirebase: AuthFirebase

    fun getLoggedUser(): Maybe<UserBean> {
        val signedUser = FirebaseAuth.getInstance().currentUser
        //val account = GoogleSignIn.getLastSignedInAccount(ctx)
        return if (signedUser == null) {
            Maybe.empty()
        } else {
            getUser(signedUser.uid)
                    .doOnSuccess { user -> Session.user = user }
        }
    }

    fun signInUser(signInData: Intent): Maybe<UserBean> {
        val task = GoogleSignIn.getSignedInAccountFromIntent(signInData)

        return try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            authFirebase.signIn(credential)
                    .doOnSuccess { user ->
                        account.displayName?.let { user.name = it }
                        account.email?.let { user.email = it }
                        account.id?.let { user.accountKey = it }
                        account.photoUrl?.let { user.photoUrl = it.toString() }
                    }
                    .observeOn(Schedulers.io())
                    .doOnSuccess { user -> userDao.save(user).subscribe() }
                    .doOnSuccess { user -> Session.user = user }
        } catch (e: ApiException) {
            Maybe.error(e)
        }
    }

    fun signOut(success: () -> Unit, failure: (Exception) -> Unit) {
        getSignInClient().signOut().addOnCompleteListener { success() }.addOnFailureListener { failure(it) }
    }

    fun getSignInClient(): GoogleSignInClient = GoogleSignIn.getClient(ctx, prepareOptions())

    private fun getUser(id: String): Maybe<UserBean> = userDao.getById(id)

    private fun prepareOptions(): GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(ctx.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
}