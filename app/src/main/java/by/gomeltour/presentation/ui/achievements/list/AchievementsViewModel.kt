package by.gomeltour.presentation.ui.achievements.list

import android.location.Geocoder
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.gomeltour.model.AchievementModel
import by.gomeltour.model.LocationModel
import by.gomeltour.repository.AchievementsRepo
import by.gomeltour.service.AchievementsService
import by.gomeltour.service.LocationsService
import by.gomeltour.service.Session
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.*
import kotlin.math.abs

class AchievementsViewModel(
        private val achievementsService: AchievementsService,
        private val locationsService: LocationsService,
        private val geocoder: Geocoder,
        private val locationClient: FusedLocationProviderClient
) : ViewModel() {
    companion object {
        private const val LOCATION_REQUEST_INTERVAL = 10_000L
    }

    val permissionRequestLiveData = MutableLiveData<Boolean>()

    val locations = ObservableArrayList<LocationModel>()
    val achievements = ObservableArrayList<AchievementModel>()
    val progressLocation = ObservableBoolean(false)
    val progressClosestLocations = ObservableBoolean(false)
    val currentLocation = ObservableField<LatLng>()
    val currentPlace = ObservableField<String?>("")
    val locationEnabled = ObservableBoolean(false)
    val showPermissionButton = ObservableBoolean(false)

    private val locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).setInterval(LOCATION_REQUEST_INTERVAL)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result?.let {
                progressLocation.set(false)
                handleLocation(LatLng(it.lastLocation.latitude, it.lastLocation.longitude))
            }
        }
    }

    init {
        loadAchievements()
    }

    fun onViewActive() {
        requestLocationPermission()
        loadAchievements()
    }

    fun onViewInactive() {
        locationClient.removeLocationUpdates(locationCallback)
    }

    fun onPermissionResult(result: Boolean) {
        if (result) {
            getCurrentLocation()
            locationEnabled.set(true)
            showPermissionButton.set(false)
        } else {
            locationEnabled.set(false)
            showPermissionButton.set(true)
        }
    }

    fun requestLocationPermission() {
        permissionRequestLiveData.postValue(true)
    }

    private fun getCurrentLocation() {
        progressLocation.set(true)

        locationClient.lastLocation.addOnCompleteListener { task ->
            val loc = task.result
            if (task.isSuccessful && loc != null) {
                handleLocation(LatLng(loc.latitude, loc.longitude))
                progressLocation.set(false)
            }
        }
/*
        locationClient.locationAvailability.addOnCompleteListener { task ->
            val result = task.result
            if (task.isSuccessful && result != null && result.isLocationAvailable) {
                //todo show location not enabled
            }
        }*/
        locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun handleLocation(latLng: LatLng) {
        if (!isSameLocation(latLng, currentLocation.get())) {
            decodeLocation(latLng)
            loadLocations(latLng)
        }
    }

    private fun decodeLocation(latLng: LatLng) {
        currentLocation.set(latLng)

        geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5)
                .firstOrNull { it.getAddressLine(0) != null }
                ?.let { address ->
                    currentPlace.set(address.getAddressLine(0))
                } ?: let { currentLocation.set(null) }
    }

    private fun loadAchievements() {
        achievementsService.listAllByUser(Session.user!!.id)
                .onEach { achievements.apply { clear(); addAll(it) } }
                .onStart { }
                .catch { }
                .onCompletion { }
                .launchIn(viewModelScope)
    }

    private fun loadLocations(location: LatLng) {
        locationsService.listClosest(location)
                .onEach {
                    locations.apply { clear(); addAll(it) }
                }
                .onStart { progressClosestLocations.set(true) }
                .catch { }
                .onCompletion { progressClosestLocations.set(false) }
                .launchIn(viewModelScope)
    }

    private fun isSameLocation(location: LatLng, locationOld: LatLng?) =
            locationOld != null && abs(locationOld.latitude - location.latitude) < 0.002 && abs(locationOld.longitude - location.longitude) < 0.02

}