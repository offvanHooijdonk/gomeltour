package by.gomeltour.presentation.ui.achievements

import android.content.Context
import android.location.Geocoder
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.gomeltour.model.AchievementModel
import by.gomeltour.model.LocationModel
import by.gomeltour.repository.LocationRepo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class AchievementsViewModel(
        private val ctx: Context,
        private val locationRepo: LocationRepo,
        private val geocoder: Geocoder,
        private val locationClient: FusedLocationProviderClient
) : ViewModel() {

    val permissionRequestLiveData = MutableLiveData<Boolean>()

    val locations = ObservableArrayList<LocationModel>()
    val achievents = ObservableArrayList<AchievementModel>()
    val locationProgress = ObservableBoolean(false)
    val currentLocation = ObservableField<LatLng>()
    val currentPlace = ObservableField<String?>()
    val locationEnabled = ObservableBoolean(false)

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result?.let {
                locationProgress.set(false)
                handleLocation(LatLng(it.lastLocation.latitude, it.lastLocation.longitude))
            }
        }
    }

    init {
        // todo
    }

    fun onViewActive() {
        requestLocationPermission()
    }

    fun onViewInactive() {

    }

    fun onPermissionResult(result: Boolean) {
        if (result) {
            getCurrentLocation()
            locationEnabled.set(true)
        } else {
            locationEnabled.set(false)
        }
    }

    fun requestLocationPermission() {
        locationEnabled.set(false)

        permissionRequestLiveData.postValue(true)
    }

    private fun getCurrentLocation() {
        locationProgress.set(true)

        locationClient.lastLocation.addOnCompleteListener { task ->
            val loc = task.result
            if (task.isSuccessful && loc != null) {
                locationProgress.set(false)
                handleLocation(LatLng(loc.latitude, loc.longitude))
            }
        }
/*
        locationClient.locationAvailability.addOnCompleteListener { task ->
            val result = task.result
            if (task.isSuccessful && result != null && result.isLocationAvailable) {
                //todo show location not enabled
            }
        }*/
        locationClient.removeLocationUpdates(locationCallback)
    }

    private fun handleLocation(latLng: LatLng) {
        decodeLocation(latLng)

        loadLocations()
    }

    private fun decodeLocation(latLng: LatLng) {
        currentLocation.set(latLng)
        currentPlace.set(null)

        geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5).firstOrNull { it.maxAddressLineIndex > 0 }
                ?.let { address ->
                    currentPlace.set(address.getAddressLine(0))
                }
    }

    private fun loadLocations() {
        locationRepo.listAll()
                .onEach {
                    locations.apply { clear(); addAll(it) }
                }
                .catch { }
                .onCompletion { }
                .launchIn(viewModelScope)
    }
}