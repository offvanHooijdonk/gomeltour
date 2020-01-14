package by.gomeltour.presentation.ui.profile

import androidx.databinding.ObservableField
import by.gomeltour.model.UserModel
import by.gomeltour.helper.attachTo
import by.gomeltour.service.profile.ProfileService
import by.gomeltour.presentation.ui.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class ProfileViewModel(private val profileService: ProfileService) : BaseViewModel() {
    override val cd: CompositeDisposable = CompositeDisposable()
    private var userId: String? = null

    val user = ObservableField<UserModel>()

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