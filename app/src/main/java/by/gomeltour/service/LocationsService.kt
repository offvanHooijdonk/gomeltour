package by.gomeltour.service

import android.location.Location
import by.gomeltour.model.LocationModel
import by.gomeltour.repository.CheckInRepo
import by.gomeltour.repository.LocationRepo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationsService(private val repo: LocationRepo, private val checkInRepo: CheckInRepo) {

    fun listClosest(location: LatLng): Flow<List<LocationModel>> = repo.listAll()
            .map { list ->
                val results = FloatArray(3)
                list.forEach {
                    Location.distanceBetween(location.latitude, location.longitude, it.latitude, it.longitude, results)
                    it.distance = results[0]
                }
                list.sortedBy { it.distance }
            }

    fun getLocationInfo(locationId: String) = repo.getById(locationId)

    fun listChecksIn(locationId: String, userId: String) = checkInRepo.listByUser(locationId, userId)

    fun checkIn(locationId: String, userId: String) = checkInRepo.addCheckIn(locationId, userId)
}