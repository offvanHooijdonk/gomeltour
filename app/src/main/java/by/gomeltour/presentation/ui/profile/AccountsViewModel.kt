package by.gomeltour.presentation.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import by.gomeltour.domain.UserBean
import by.gomeltour.helper.attachTo
import by.gomeltour.model.Session
import by.gomeltour.model.profile.ProfileService
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class AccountsViewModel(private val profileService: ProfileService, private val routerHelper: RouterHelper) : BaseViewModel() {
    override val cd = CompositeDisposable()

    val closeCommand = MutableLiveData<Unit>()
    val user = ObservableField<UserBean>()

    init {
        profileService.currentProfile
                .subscribe { user.set(it) }
                .attachTo(cd)
    }

    fun start() {
        profileService.getCurrentProfile(Session.user!!.id)
    }

    fun onAccountPressed() {
        routerHelper.navigateToProfile(Session.user!!.id)
        closeCommand.postValue(Unit)
    }
}