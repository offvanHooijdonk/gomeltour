package by.gomeltour.presentation.ui.achievements

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.gomeltour.model.LocationModel
import by.gomeltour.repository.LocationRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class AchievementsViewModel(private val locationRepo: LocationRepo) : ViewModel() {

    val locations = ObservableArrayList<LocationModel>()

    init {
        loadLocations()
    }

    private fun loadLocations() {
        locationRepo.listAll()
                .onEach {
                    locations.apply { clear(); addAll(it) }
                }
                .catch {  }
                .onCompletion {  }
                .launchIn(viewModelScope)
    }
}