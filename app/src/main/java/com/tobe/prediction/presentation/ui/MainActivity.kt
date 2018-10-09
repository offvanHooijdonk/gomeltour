package com.tobe.prediction.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tobe.prediction.R
import com.tobe.prediction.di.dependency
import com.tobe.prediction.helper.colorError
import com.tobe.prediction.presentation.presenter.main.MainPresenter
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleFragment
import kotlinx.android.synthetic.main.act_main.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by Yahor_Fralou on 9/18/2018 5:15 PM.
 */

class MainActivity : AppCompatActivity(), IMainView {
    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        dependency().mainComponent().inject(this)
        presenter.attachView(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) showBackButton(false)
        }
        navigate(FRAG_PREDICT_LIST, null)
    }

    private fun logOut() {
        presenter.onLogoutSelected()
    }

    override fun navigateLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.it_sign_out -> showLogOutDialog()
            android.R.id.home -> supportFragmentManager.popBackStack()
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun showError(e: Exception) {
        errorBar("Error! Log out failed.")
        toast(e.toString())
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }

    private fun navigate(destKey: String, bundle: Bundle?) {
        val fr: Fragment
        fr = when (destKey) {
            FRAG_PREDICT_LIST -> PredictListFragment.instance(pick = { predictId -> startPredictView(predictId) })
            FRAG_PREDICT_VIEW -> PredictSingleFragment()
            else -> return
        }
        fr.arguments = bundle

        supportFragmentManager.beginTransaction()
                .apply { if (destKey != FRAG_PREDICT_LIST) setCustomAnimations(R.anim.screen_slide_rl_in, R.anim.screen_slide_rl_out, R.anim.screen_slide_lr_in, R.anim.screen_slide_lr_out) }
                .replace(containerMain.id, fr, destKey)
                .apply { if (destKey != FRAG_PREDICT_LIST) addToBackStack(destKey) }
                .commit()

        showBackButton(destKey != FRAG_PREDICT_LIST)
    }

    private fun startPredictView(predictId: String) {
        navigate(FRAG_PREDICT_VIEW, Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) })
    }

    private fun showLogOutDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.sign_out_title)
                .setMessage(R.string.sign_out_message)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok) { dialog, _ -> logOut(); dialog.dismiss() }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .show()
        /*alert {
                titleResource = R.string.sign_out_title
                messageResource = R.string.sign_out_message
                isCancelable = true
                okButton { dialog ->
                    logOut()
                    dialog.dismiss()
                }
                cancelButton { dialog -> dialog.dismiss() }
            }.show()*/
    }

    private fun showBackButton(isShow: Boolean) {
        Handler().postDelayed(Runnable {
            supportActionBar?.setDisplayShowHomeEnabled(isShow)
            supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        }, if (isShow) 350 else 0)
    }

    private fun errorBar(message: String) {
        containerMain.snackbar(message).colorError()
    }
}

private const val FRAG_PREDICT_VIEW = "predict_view"
private const val FRAG_PREDICT_LIST = "predict_list"