package by.gomeltour.service.profile

import by.gomeltour.dao.UserDao
import by.gomeltour.model.UserModel
import by.gomeltour.helper.launchScopeIO
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

class ProfileService(private val userDao: UserDao) {

    val profileInfo: Observable<UserModel>
        get() = obsProfileInfo

    val currentProfile: Observable<UserModel>
        get() = obsCurrentProfile

    private val obsProfileInfo = PublishSubject.create<UserModel>()
    private val obsCurrentProfile = PublishSubject.create<UserModel>()

    fun getProfileInfo(userId: String) {
        getProfile(userId, obsProfileInfo)
    }

    fun getCurrentProfile(userId: String) {
        getProfile(userId, obsCurrentProfile)
    }

    private fun getProfile(userId: String, observer: Observer<UserModel>) {
        launchScopeIO {
            val user = userDao.getByIdSync(userId)
            observer.onNext(user)
        }
    }
}