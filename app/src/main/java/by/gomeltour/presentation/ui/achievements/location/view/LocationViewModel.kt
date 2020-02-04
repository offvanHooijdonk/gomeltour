package by.gomeltour.presentation.ui.achievements.location.view

import android.text.format.DateUtils
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.gomeltour.model.CheckInModel
import by.gomeltour.model.LocationModel
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.service.AchievementsService
import by.gomeltour.service.LocationsService
import by.gomeltour.service.Session
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class LocationViewModel(
        private val locationsService: LocationsService,
        private val achievementsService: AchievementsService,
        private val routerHelper: RouterHelper
) : ViewModel() {
    val location = ObservableField<LocationModel>()
    val idProvided = ObservableBoolean(true)
    val found = ObservableBoolean(true)
    val checkedToday = ObservableBoolean(false)
    val checksList = ObservableArrayList<CheckInModel>()

    private lateinit var locationId: String

    fun onViewStart(locationId: String?) {
        if (locationId != null) {
            this.locationId = locationId

            loadLocationInfo(locationId)
            loadLocationChecks(locationId)
        } else {
            idProvided.set(false)
        }
    }

    fun checkIn() {
        achievementsService.listAchievedByUser(Session.user!!.id)
                .flatMapConcat { list ->
                    locationsService.checkIn(locationId, Session.user!!.id).onEach { loadLocationChecks(locationId) }.map { list }
                }.flatMapConcat { listOld ->
                    achievementsService.listAchievedByUser(Session.user!!.id)
                            .map { it.firstOrNull { item -> !listOld.contains(item) } }
                    //listNew.firstOrNull { !listOld.contains(it) }
                }.onEach { newItem ->
                    newItem?.let {
                        routerHelper.navigateToEarned(it.id)
                    }
                }
                //locationsService.checkIn(locationId, Session.user!!.id)
                //.onEach { loadLocationChecks(locationId) }
                .launchIn(viewModelScope)
    }

    private fun loadLocationInfo(locationId: String) {
        locationsService.getLocationInfo(locationId)
                .onEach {
                    if (it != null) {
                        location.set(it)
                        found.set(true)
                    } else found.set(false)
                }
                .launchIn(viewModelScope)
    }

    private fun loadLocationChecks(locationId: String) {
        locationsService.listChecksIn(locationId, Session.user!!.id)
                .onEach {
                    checksList.apply { clear(); addAll(it) }
                    checkedToday.set(isCheckedToday())
                }
                .launchIn(viewModelScope)
    }

    private fun isCheckedToday(): Boolean {
        val lastCheck = checksList.lastOrNull()

        return lastCheck?.let { DateUtils.isToday(lastCheck.checkTimestamp.time) } ?: false
    }
}