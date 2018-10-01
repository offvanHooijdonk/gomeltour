package com.tobe.prediction.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.tobe.prediction.R
import com.tobe.prediction.di.dependency
import com.tobe.prediction.helper.colorError
import com.tobe.prediction.presentation.presenter.main.MainPresenter
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import kotlinx.android.synthetic.main.act_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.okButton
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

        supportFragmentManager.beginTransaction().replace(containerMain.id, PredictListFragment()).commit() // todo make through presenter calls
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
            R.id.it_sign_out -> alert {
                titleResource = R.string.sign_out_title
                messageResource = R.string.sign_out_message
                isCancelable = true
                okButton { dialog ->
                    logOut()
                    dialog.dismiss()
                }
                cancelButton { dialog -> dialog.dismiss() }
            }.show()
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

    private fun errorBar(message: String) {
        containerMain.snackbar(message).colorError()
    }
}