package by.gomeltour.presentation.ui.achievements.location.view

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import by.gomeltour.model.CheckInModel

class ItemChekInViewModel : ViewModel() {
    val checkIn = ObservableField<CheckInModel>()
}