package by.gomeltour.presentation.ui.achievements.view

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.gomeltour.model.AchievementModel
import by.gomeltour.presentation.navigation.RouterHelper
import by.gomeltour.repository.AchievementsRepo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EarnedAchievementViewModel(private val repo: AchievementsRepo) : ViewModel() {
    val achievement = ObservableField<AchievementModel>()

    fun loadData(achievementId: String) {
        repo.getById(achievementId)
                .onEach { achievement.set(it) }
                .launchIn(viewModelScope)
    }
}