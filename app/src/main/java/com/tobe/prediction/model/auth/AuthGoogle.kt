package com.tobe.prediction.model.auth

import android.arch.persistence.room.EmptyResultSetException
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.tobe.prediction.dao.UserDao
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.model.Session
import io.reactivex.Maybe
import io.reactivex.Single
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
        val account = GoogleSignIn.getLastSignedInAccount(ctx)
        // todo get data from DB
        return if (account == null) {
            Maybe.empty()
        } else {
            //Maybe.just(UserBean(accountKey = account.id ?: "-", name = account.displayName ?: "-"))
            getUser(account)
        }
    }

    fun signInUser(signInData: Intent): Single<UserBean> {
        val task = GoogleSignIn.getSignedInAccountFromIntent(signInData)

        return try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val user = UserBean(accountKey = account.id!!, name = account.displayName
                    ?: account.id!!) // todo create user with special class
            Single.fromCallable { userDao.save(user) }
                    .map { id -> user.also { it.id = id } }
                    .doOnSuccess { Session.user = user }
        } catch (e: ApiException) {
            Single.error(e)
        }
    }

    fun getSignInClient(): GoogleSignInClient = GoogleSignIn.getClient(ctx, prepareOptions())

    private fun getUser(account: GoogleSignInAccount): Maybe<UserBean> = userDao.getByKey(account.id!!)
            .toMaybe()
            .onErrorResumeNext { th: Throwable ->
                if (th is EmptyResultSetException)
                    Maybe.empty<UserBean>()
                else
                    Maybe.error<UserBean>(th)
            }


    private fun prepareOptions(): GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
}