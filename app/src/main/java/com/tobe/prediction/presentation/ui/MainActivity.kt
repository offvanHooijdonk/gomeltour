package com.tobe.prediction.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.tobe.prediction.R
import com.tobe.prediction.model.Session
import com.tobe.prediction.presentation.presenter.main.MainPresenter
import com.tobe.prediction.presentation.ui.login.LoginActivity
import kotlinx.android.synthetic.main.act_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton

/**
 * Created by Yahor_Fralou on 9/18/2018 5:15 PM.
 */

class MainActivity : AppCompatActivity(), IMainView {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        presenter = MainPresenter(this)
        presenter.inject(this)
        txtHello.text = "${Session.user?.id} : ${Session.user?.name}"
    }

    private fun logOut() {
        presenter.onLogoutSelected()
    }

    override fun navigateLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent) // todo check this removed from history
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
}