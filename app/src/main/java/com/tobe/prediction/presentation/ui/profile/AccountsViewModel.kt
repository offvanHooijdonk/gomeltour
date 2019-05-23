package com.tobe.prediction.presentation.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.Session
import com.tobe.prediction.model.profile.ProfileService
import com.tobe.prediction.presentation.navigation.RouterHelper
import com.tobe.prediction.presentation.ui.BaseViewModel
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