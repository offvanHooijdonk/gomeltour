package by.gomeltour.model.profile

import by.gomeltour.dao.UserDao
import by.gomeltour.domain.UserBean
import by.gomeltour.helper.launchScopeIO
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

class ProfileService(private val userDao: UserDao) {

    val profileInfo: Observable<UserBean>
        get() = obsProfileInfo

    val currentProfile: Observable<UserBean>
        get() = obsCurrentProfile

    private val obsProfileInfo = PublishSubject.create<UserBean>()
    private val obsCurrentProfile = PublishSubject.create<UserBean>()

    fun getProfileInfo(userId: String) {
        getProfile(userId, obsProfileInfo)
    }

    fun getCurrentProfile(userId: String) {
        getProfile(userId, obsCurrentProfile)
    }

    private fun getProfile(userId: String, observer: Observer<UserBean>) {
        launchScopeIO {
            val user = userDao.getByIdSync(userId)
            observer.onNext(user)
        }
    }
}