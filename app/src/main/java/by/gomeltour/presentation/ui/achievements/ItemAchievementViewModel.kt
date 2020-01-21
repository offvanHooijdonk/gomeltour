package by.gomeltour.presentation.ui.achievements

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import by.gomeltour.model.AchievementModel

class ItemAchievementViewModel : ViewModel() {

    val achievement = ObservableField<AchievementModel>()
}