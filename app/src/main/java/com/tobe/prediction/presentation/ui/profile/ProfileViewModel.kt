package com.tobe.prediction.presentation.ui.profile

import androidx.databinding.ObservableField
import com.tobe.prediction.domain.UserBean
import com.tobe.prediction.helper.attachTo
import com.tobe.prediction.model.profile.ProfileService
import com.tobe.prediction.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class ProfileViewModel(private val profileService: ProfileService) : BaseViewModel() {
    override val cd: CompositeDisposable = CompositeDisposable()
    private var userId: String? = null

    val user = ObservableField<UserBean>()

    init {
        profileService.profileInfo.subscribe {
            user.set(it)
        }.attachTo(cd)
    }

    fun setupUser(userId: String) {
        if (this.userId != userId) {
            this.userId = userId

            this.userId?.let {
                profileService.getProfileInfo(it)
            }
        }
    }
}