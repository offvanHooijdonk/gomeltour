package by.gomeltour.presentation.ui.achievements

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import by.gomeltour.model.LocationModel

class ItemLocationViewModel : ViewModel() {

    val location = ObservableField<LocationModel>()
}