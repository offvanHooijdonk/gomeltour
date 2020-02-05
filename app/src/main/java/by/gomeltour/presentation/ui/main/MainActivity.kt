package by.gomeltour.presentation.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import by.gomeltour.R
import by.gomeltour.app.App.Companion.LOGCAT
import by.gomeltour.databinding.ActMainBinding
import by.gomeltour.presentation.navigation.BaseSupportAppNavigator
import by.gomeltour.presentation.navigation.NavigationBackStack
import by.gomeltour.presentation.navigation.Screens
import com.google.android.material.snackbar.Snackbar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActMainBinding>(this, R.layout.act_main)
        binding.model = viewModel

        setSupportActionBar(toolbar)
        supportActionBar?.title = null
/*
        nav_bottom.setOnNavigationItemSelectedListener { item ->
            viewModel.onNavSelected(item)
            true
        }*/
        navigatorHolder.setNavigator(navigator)

        val navController = findNavController(R.id.containerMain)
        nav_bottom.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(
                this,
                navController, AppBarConfiguration(setOf(R.id.it_events, R.id.it_museums, R.id.it_achievements))
        )

        //viewModel.viewStart()
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

    /*override fun onBackPressed() {
        //viewModel.back()
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem?): Boolean { // FIXME remove if unused
        when (item?.itemId) {
            android.R.id.home -> viewModel.back()
        }

        return true
    }*/

    fun setTitle(title: String) {
        toolbar_title.text = title
    }

    fun createSnackbar(text: String, duration: Int) = Snackbar.make(coordinator_layout, text, duration).setAnchorView(nav_bottom)

    private fun showBackButton(isShow: Boolean) {
        Handler().postDelayed(Runnable {
            supportActionBar?.setDisplayShowHomeEnabled(isShow)
            supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        }, if (isShow) 350 else 0)
    }

    private inner class MainNavigator(private val backStack: NavigationBackStack, act: FragmentActivity, private val fm: FragmentManager, containerId: Int)
        : BaseSupportAppNavigator(backStack, act, fm, containerId) {

        private val screensHideBack = setOf(Screens.Keys.EVENT_LIST.name, Screens.Keys.ACHIEVEMENTS.name)

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
            backStack.currentScreen?.let {
                if (it.screenKey in screensHideBack) showBackButton(false) else showBackButton(true)
            }
        }

        override fun setupFragmentTransaction(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
            super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)

            //if (nextFragment !is EventListFragment) {
            //fragmentTransaction?.setCustomAnimations(R.anim.screen_slide_rl_in, R.anim.screen_slide_rl_out, R.anim.screen_slide_lr_in, R.anim.screen_slide_lr_out)
            fragmentTransaction?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            //}
        }
    }
}