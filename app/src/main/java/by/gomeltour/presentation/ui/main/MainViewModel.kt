package by.gomeltour.presentation.ui.main

import android.view.MenuItem
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import by.gomeltour.R
import by.gomeltour.helper.attachTo
import by.gomeltour.service.Session
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import by.gomeltour.presentation.ui.main.screenevents.ListScrollEvent
import by.gomeltour.presentation.ui.main.screenevents.ScreenEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
        private val routerHelper: RouterHelper,
        private val screenEvents: Observable<ScreenEvent>
) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val errorMsg = ObservableField<String>()
    val extendState = ObservableBoolean(true)
    val showAddButton = ObservableBoolean(true)
    val userPhotoUrl = ObservableField<String>(Session.user?.photoUrl)

    fun viewStart() {
        routerHelper.navigateToEventList()

        screenEvents.subscribe { event ->
            when (event) {
                is ListScrollEvent -> if (event.isDown) extendState.set(false) else extendState.set(true)
            }
        }.attachTo(cd)
    }

    fun onNavSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.it_events -> {
                routerHelper.navigateToEventList()
            }
            R.id.it_more -> {
                routerHelper.navigateToOptions()
            }
            R.id.it_museums -> {
                //routerHelper.navigateToGame()
            }
        }
    }

    fun onAppBarUserClick() {
        routerHelper.navigateToAccounts()
    }

    fun back() {
        routerHelper.navigateBack()
    }
}