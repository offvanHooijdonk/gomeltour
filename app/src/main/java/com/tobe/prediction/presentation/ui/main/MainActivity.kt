package com.tobe.prediction.presentation.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.tobe.prediction.R
import com.tobe.prediction.app.App.Companion.LOGCAT
import com.tobe.prediction.databinding.ActMainBinding
import com.tobe.prediction.presentation.navigation.BaseSupportAppNavigator
import com.tobe.prediction.presentation.navigation.NavigationBackStack
import com.tobe.prediction.presentation.navigation.PredictSingleScreen
import com.tobe.prediction.presentation.navigation.Screens
import com.tobe.prediction.presentation.ui.predict.list.PredictListFragment
import kotlinx.android.synthetic.main.act_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

/**
 * Created by Yahor_Fralou on 9/18/2018 5:15 PM.
 */

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = MainNavigator(get(), this, supportFragmentManager, R.id.containerMain)
    private lateinit var transformer: EFABTransformer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActMainBinding>(this, R.layout.act_main)
        binding.model = viewModel

        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        transformer = EFABTransformer(R.drawable.ic_wand_24, fabAddNew)

        navBottom.setOnNavigationItemSelectedListener { item ->
            viewModel.onNavSelected(item)
            item.itemId != R.id.it_more
        }

        navigatorHolder.setNavigator(navigator)
        viewModel.viewStart()
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

    private fun logOut() {
        viewModel.logOut()
    }

    private fun showBackButton(isShow: Boolean) {
        Handler().postDelayed(Runnable {
            supportActionBar?.setDisplayShowHomeEnabled(isShow)
            supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        }, if (isShow) 350 else 0)
    }

    private inner class MainNavigator(private val backStack: NavigationBackStack, act: FragmentActivity, private val fm: FragmentManager, containerId: Int)
        : BaseSupportAppNavigator(backStack, act, fm, containerId) {

        private val screensHideBack = setOf(Screens.Keys.PREDICT_LIST.name)
        private val screensSkipBack = emptySet<String>()

        override fun applyCommand(command: Command?) {
            super.applyCommand(command)
            checkEnableHomeButton()
        }

        override fun fragmentForward(command: Forward?) {
            val fragment = createFragment(command?.screen as SupportAppScreen)
            if (fragment is DialogFragment) {
                fragment.show(fm, command.screen.screenKey)
            } else {
                super.fragmentForward(command)
            }
        }

        private fun checkEnableHomeButton() {
            backStack.currentScreen?.also {
                if (it.screenKey in screensHideBack) {
                    showBackButton(false)
                } else if (it.screenKey !in screensSkipBack) {
                    showBackButton(true)
                    //viewModel.showAddButton(false)
                }

                if (it.screenKey == Screens.Keys.PREDICT_SINGLE.name) { // todo simplify and refactor
                    (it as? PredictSingleScreen)?.let { _ ->
                        // todo check if current user is author
                        transformer.transformToFAB(R.drawable.ic_edit_24)
                        appBarLayout.setExpanded(true, true)
                    }
                } else if (it.screenKey == Screens.Keys.PREDICT_LIST.name) {
                    if (fabAddNew.isShown) {
                        transformer.transformBack()
                    } else {
                        fabAddNew.show()
                    }
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