package com.tobe.prediction.presentation.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tobe.prediction.R
import com.tobe.prediction.app.App.Companion.LOGCAT
import com.tobe.prediction.databinding.ActMainBinding
import com.tobe.prediction.presentation.navigation.BaseSupportAppNavigator
import com.tobe.prediction.presentation.navigation.NavigationBackStack
import com.tobe.prediction.presentation.navigation.Screens
import com.tobe.prediction.presentation.ui.predict.edit.PredictEditDialog
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import kotlinx.android.synthetic.main.act_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Command

/**
 * Created by Yahor_Fralou on 9/18/2018 5:15 PM.
 */

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG_EDIT_DIALOG = "tag_edit_dialog"
        private const val FRAG_BOTTOM_NAVIGATION = "bottom_navigation"
    }

    private val viewModel: MainViewModel by viewModel()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = MainNavigator(get(), this, supportFragmentManager, R.id.containerMain)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActMainBinding>(this, R.layout.act_main)
        binding.model = viewModel

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

        navigatorHolder.setNavigator(navigator)
        viewModel.viewStart()
        //navigate(FRAG_PREDICT_LIST, null)
    }

    override fun onStart() {
        super.onStart()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onStop() {
        super.onStop()

        navigatorHolder.removeNavigator()
        Log.i(LOGCAT, "Main Nav removed")
    }

    override fun onBackPressed() {
        viewModel.back()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean { // FIXME remove if unused
        when (item?.itemId) {
            android.R.id.home -> viewModel.back()
        }

        return true
    }

    private fun startBottomMenu() {
        BottomOptionsDialog()
                .apply { setMenuPickListener { option -> onBottomOptionsMenuPick(option) } }
                .show(supportFragmentManager, "options_menu")
    }

    private fun logOut() {
        viewModel.logOut()
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

    private fun onScreenScrolled(isDown: Boolean) {
        if (isDown) fabAddNew.shrink() else fabAddNew.extend()
    }

    private fun showBackButton(isShow: Boolean) {
        Handler().postDelayed(Runnable {
            supportActionBar?.setDisplayShowHomeEnabled(isShow)
            supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        }, if (isShow) 350 else 0)
    }

    private inner class MainNavigator(private val backStack: NavigationBackStack, act: FragmentActivity, fm: FragmentManager, containerId: Int)
        : BaseSupportAppNavigator(backStack, act, fm, containerId) {

        override fun applyCommand(command: Command?) {
            super.applyCommand(command)
            checkEnableHomeButton()
        }

        private fun checkEnableHomeButton() {
            backStack.currentScreen?.also {
                if (it.screenKey != Screens.Keys.PREDICT_LIST.name) {
                    showBackButton(true)
                    viewModel.showAddButton(false)
                } else {
                    showBackButton(false)
                    viewModel.showAddButton(true)
                }
            }
        }

        override fun setupFragmentTransaction(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
            super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)

            if (nextFragment !is PredictListFragment) {
                fragmentTransaction?.setCustomAnimations(R.anim.screen_slide_rl_in, R.anim.screen_slide_rl_out, R.anim.screen_slide_lr_in, R.anim.screen_slide_lr_out)
            }
        }
    }
}