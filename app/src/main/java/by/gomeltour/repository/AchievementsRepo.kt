package by.gomeltour.repository

import by.gomeltour.dao.AchievementsDao
import by.gomeltour.model.AchievementLocationsModel
import by.gomeltour.model.AchievementModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AchievementsRepo(private val dao: AchievementsDao) {

    fun getById(id: String) = flow {
        emit(dao.getById(id))
    }.flowOn(Dispatchers.IO)

    fun upsertBatch(list: List<AchievementModel>): Flow<Unit> = flow {
        list.forEach {
            if (dao.insert(it) == -1L) dao.update(it)
            dao.deleteLocations(it.id)
            it.locations.forEach { loc ->
                dao.insertLocation(AchievementLocationsModel(0, it.id, loc))
            }
        }
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    fun listAll(): Flow<List<AchievementModel>> = flow {
        emit(dao.listAll())
    }.flowOn(Dispatchers.IO)

    fun listAchieved(userId: String) = flow {
        emit(dao.listAchieved(userId))
    }.flowOn(Dispatchers.IO)
}