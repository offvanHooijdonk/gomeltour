package com.tobe.prediction.presentation.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.tobe.prediction.R
import com.tobe.prediction.di.dependency
import com.tobe.prediction.helper.colorError
import com.tobe.prediction.helper.hide
import com.tobe.prediction.helper.show
import com.tobe.prediction.presentation.presenter.main.MainPresenter
import com.tobe.prediction.presentation.ui.login.LoginActivity
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import com.tobe.prediction.presentation.ui.predict.view.PredictEditDialog
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleDialog
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

        bottomAppBar.inflateMenu(R.menu.main)
        bottomAppBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.it_bottom_menu) startBottomMenu()
            return@setOnMenuItemClickListener true
        }

        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        bottomAppBar.setNavigationOnClickListener {
            BottomNavigationDialog().show(supportFragmentManager, FRAG_BOTTOM_NAVIGATION)
        }

        fabAddNew.setOnClickListener {
            PredictEditDialog().show(supportFragmentManager, "one")
        }

        // TODO remove when we do not do fragments navigation
        /*supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) showBackButton(false)
        }*/
        navigate(FRAG_PREDICT_LIST, null)
    }

    private fun startBottomMenu() {
        BottomOptionsDialog()
                .apply { setMenuPickListener { option -> onBottomOptionsMenuPick(option) } }
                .show(supportFragmentManager, "options_menu")
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
            //android.R.id.home -> supportFragmentManager.popBackStack()
            //android.R.id.home -> BottomNavigationDialog().show(supportFragmentManager, FRAG_BOTTOM_NAVIGATION)
        }

        return true
    }

    private fun onBottomOptionsMenuPick(option: Int) {
        with(BottomOptionsDialog) {
            when (option) {
                EVENT_SIGN_OUT -> logOut()
                else -> {
                }
            }
        }
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
                    .apply { scroll = { isDown: Boolean -> if (isDown) animateFABOut() else animateFABIn() } }
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

    private fun animateFABOut() {
        val durationShrink = 250L

        /*fabAddNew.layoutParams = (fabAddNew.layoutParams as CoordinatorLayout.LayoutParams)
                .apply {
                    anchorGravity = Gravity.TOP
                    marginStart = fabAddNew.left
                }*/
        val animScaleFade = ValueAnimator.ofFloat(0.9f, 0.0f)
                .apply {
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        val scale = 1 - (1 - value) * 0.8f
                        fabAddNew.scaleX = scale
                        fabAddNew.scaleY = scale
                        fabAddNew.alpha = 1 - (1 - value) * 0.8f
                        if (it.animatedFraction == 1.0f) {
                            fabAddNew.hide()
                        }
                    }
                    duration = 150
                }

        ValueAnimator.ofInt(fabAddNew.width, fabAddNew.height) // TODO try move left side to screen center faster, then the button shrinks
                .apply {
                    interpolator = DecelerateInterpolator(2f)
                    duration = durationShrink
                    addUpdateListener {
                        fabAddNew.layoutParams.apply {
                            width = it.animatedValue as Int; fabAddNew.layoutParams = this
                        }
                        if (it.animatedFraction > 0.97f && !animScaleFade.isRunning) {
                            animScaleFade.start()
                        }
                    }
                }.start()
    }

    private fun animateFABIn() {
        fabAddNew.layoutParams = (fabAddNew.layoutParams as CoordinatorLayout.LayoutParams)
                .apply {
                    //anchorGravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                    //marginStart = 0
                    width = CoordinatorLayout.LayoutParams.WRAP_CONTENT
                }
        fabAddNew.show()
        fabAddNew.scaleX = 1f
        fabAddNew.scaleY = 1f
        fabAddNew.alpha = 1f
    }

    private fun startPredictView(predictId: String) {
        //navigate(FRAG_PREDICT_VIEW, Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) })
        PredictSingleDialog().apply {
            arguments = Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) }
        }.show(supportFragmentManager, FRAG_PREDICT_VIEW)

    }

    @Deprecated("Now Bottom Sheet Dialog is used")
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
private const val FRAG_BOTTOM_NAVIGATION = "bottom_navigation"