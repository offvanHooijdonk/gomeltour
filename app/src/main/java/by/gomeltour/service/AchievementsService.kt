package by.gomeltour.service

import by.gomeltour.repository.AchievementsRepo
import kotlinx.coroutines.flow.zip

class AchievementsService(private val repo: AchievementsRepo) {
    fun listAllByUser(userId: String) =
            repo.listAll()
                    .zip(repo.listAchieved(userId)) { listAll, listAchieved ->
                        listAll.map { it.apply { isEarned = listAchieved.contains(it) } }.sortedBy { !it.isEarned }
                    }

    fun listAchievedByUser(userId: String) = repo.listAchieved(userId)
}