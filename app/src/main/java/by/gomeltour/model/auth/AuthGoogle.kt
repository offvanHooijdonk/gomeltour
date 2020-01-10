package by.gomeltour.model.auth

import android.content.Context
import android.content.Intent
import by.gomeltour.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import by.gomeltour.dao.UserDao
import by.gomeltour.domain.UserBean
import by.gomeltour.model.Session
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yahor_Fralou on 9/18/2018 12:48 PM.
 */

class AuthGoogle constructor(private val ctx: Context, private val userDao: UserDao, private val authFirebase: AuthFirebase) {

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
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                authFirebase.signIn(credential)
                        .doOnSuccess { user ->
                            account.displayName?.let { user.name = it }
                            account.email?.let { user.email = it }
                            account.id?.let { user.accountKey = it }
                            account.photoUrl?.let { user.photoUrl = it.toString() }
                        }
                        .observeOn(Schedulers.io())
                        .doOnSuccess { user -> userDao.save(user)/*.subscribe()*/ }
                        .doOnSuccess { user -> Session.user = user }
            } else {
                Maybe.error(Exception("No account returned"))
            }
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