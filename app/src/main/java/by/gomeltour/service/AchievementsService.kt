package by.gomeltour.service

import by.gomeltour.helper.launchScopeIO
import by.gomeltour.model.AchievementModel
import by.gomeltour.repository.AchievementsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class AchievementsService(private val repo: AchievementsRepo) {
    fun listAllByUser(userId: String) =
            repo.listAll()
                    .zip(repo.listAchieved(userId)) { listAll, listAchieved ->
                        listAll.map { it.apply { isEarned = listAchieved.contains(it) } }.sortedBy { !it.isEarned }
                    }

    fun listAllByUserPaged(userId: String) =
            repo.listAllPaged()/*.mapByPage { listAll ->
                CoroutineScope(Dispatchers.Unconfined).launch {
                    val listAchieved = //CoroutineScope(Dispatchers.Unconfined).async {
                            repo.listAchieved(userId).first()
                    //}.await()
                    listAll.map { it.apply { isEarned = listAchieved.contains(it) } }.sortedBy { !it.isEarned }
                }
                listAll
            }*/

    fun listAchievedByUser(userId: String) = repo.listAchieved(userId)
}