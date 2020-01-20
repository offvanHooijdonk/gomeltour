package by.gomeltour.repository

import by.gomeltour.dao.AchievementsDao
import by.gomeltour.model.AchievementModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AchievementsRepo(private val dao: AchievementsDao) {

    fun upsertBatch(list: List<AchievementModel>): Flow<Unit> = flow {
        list.forEach {
            if (dao.insert(it) == -1L) dao.update(it)
        }
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    fun listAll(): Flow<List<AchievementModel>> = flow {
        emit(dao.listAll())
    }.flowOn(Dispatchers.IO)
}