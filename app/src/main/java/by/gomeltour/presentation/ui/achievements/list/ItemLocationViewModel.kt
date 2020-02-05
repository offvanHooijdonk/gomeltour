package by.gomeltour.presentation.ui.achievements.list

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import by.gomeltour.model.LocationModel
import by.gomeltour.presentation.navigation.RouterHelper

class ItemLocationViewModel(private val routerHelper: RouterHelper) : ViewModel() {

    val location = ObservableField<LocationModel>()

    fun goToLocation() {
        /*location.get()?.let {
            routerHelper.navigateToLocationView(it.id)
        }*/
    }
}