package com.tobe.prediction.presentation.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tobe.prediction.R
import com.tobe.prediction.helper.colorError
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditDialog
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import com.tobe.prediction.presentation.ui.predict.view.PredictSingleFragment
import kotlinx.android.synthetic.main.act_main.*
import org.jetbrains.anko.design.snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

/**
 * Created by Yahor_Fralou on 9/18/2018 5:15 PM.
 */

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG_EDIT_DIALOG = "tag_edit_dialog"
    }

    private val viewModel: MainViewModel by viewModel()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.containerMain)

    //private lateinit var fabAnimator: FabAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

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
            PredictEditDialog().show(supportFragmentManager, TAG_EDIT_DIALOG)
        }

        //fabAnimator = FabAnimator(fabAddNew)

        navigate(FRAG_PREDICT_LIST, null)
    }

    override fun onStart() {
        super.onStart()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onStop() {
        super.onStop()

        navigatorHolder.removeNavigator()
    }

    private fun startBottomMenu() {
        BottomOptionsDialog()
                .apply { setMenuPickListener { option -> onBottomOptionsMenuPick(option) } }
                .show(supportFragmentManager, "options_menu")
    }

    private fun logOut() {
        viewModel.logOut()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean { // FIXME remove if unused
        when (item?.itemId) {
            android.R.id.home -> supportFragmentManager.popBackStack()
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

    private fun navigate(destKey: String, bundle: Bundle?) {
        val fr: Fragment
        fr = when (destKey) {
            FRAG_PREDICT_LIST -> PredictListFragment.instance(pick = { predictId -> startPredictView(predictId) })
                    .apply { scroll = { isDown: Boolean -> animateFABHiding(isDown) } }
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

    private fun animateFABHiding(isHide: Boolean) {
        if (isHide) {
            fabAddNew.hide()
        } else {
            fabAddNew.show()
        }
    }

    private fun startPredictView(predictId: String) {
        //navigate(FRAG_PREDICT_VIEW, Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) })
        /*PredictSingleDialog().apply {
            arguments = Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) }
        }.show(supportFragmentManager, FRAG_PREDICT_VIEW)*/

        navigate(FRAG_PREDICT_VIEW, Bundle().apply { putString(PredictSingleFragment.EXTRA_PREDICT_ID, predictId) })

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