package by.gomeltour.presentation.ui.main

import android.view.MenuItem
import androidx.databinding.ObservableField
import by.gomeltour.R
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import by.gomeltour.presentation.ui.main.screenevents.ScreenEvent
import by.gomeltour.service.Session
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
        private val routerHelper: RouterHelper,
        private val screenEvents: Observable<ScreenEvent>
) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val errorMsg = ObservableField<String>()
    val userPhotoUrl = ObservableField<String>(Session.user?.photoUrl)

    /*fun viewStart() {
        routerHelper.navigateToEventList()
    }*/

    fun onNavSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.it_events -> {
                routerHelper.navigateToEventList()
            }
            R.id.it_museums -> {
                //routerHelper.navigateToGame()
            }
            R.id.it_achievements -> routerHelper.navigateToAchievements()
        }
    }

    fun onAppBarUserClick() {
        routerHelper.navigateToAccounts()
    }

    fun back() {
        routerHelper.navigateBack()
    }
}