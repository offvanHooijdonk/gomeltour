package by.gomeltour.presentation.ui.achievements.location.view

import android.text.format.DateUtils
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.gomeltour.model.AchievementModel
import by.gomeltour.model.CheckInModel
import by.gomeltour.model.LocationModel
import by.gomeltour.service.AchievementsService
import by.gomeltour.service.LocationsService
import by.gomeltour.service.Session
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip

class LocationViewModel(private val locationsService: LocationsService, private val achievementsService: AchievementsService) : ViewModel() {
    val location = ObservableField<LocationModel>()
    val idProvided = ObservableBoolean(true)
    val found = ObservableBoolean(true)
    val checkedToday = ObservableBoolean(false)
    val checksList = ObservableArrayList<CheckInModel>()
    val newAchievementLiveData = MutableLiveData<AchievementModel>()

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
                .zip(locationsService.checkIn(locationId, Session.user!!.id).onEach { loadLocationChecks(locationId) }) { list, _ ->
                    list
                }.zip(achievementsService.listAchievedByUser(Session.user!!.id)) { listOld, listNew ->
                    listNew.firstOrNull { !listOld.contains(it) }
                }.onEach { newItem ->
                    newItem?.let { newAchievementLiveData.postValue(it) }
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