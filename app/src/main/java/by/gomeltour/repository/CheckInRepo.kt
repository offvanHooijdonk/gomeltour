package by.gomeltour.repository

import by.gomeltour.dao.CheckInDao
import by.gomeltour.model.CheckInModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

class CheckInRepo(private val dao: CheckInDao) {
    fun listByUser(locationId: String, userId: String): Flow<List<CheckInModel>> = flow {
        emit(dao.listByUser(locationId, userId))
    }.flowOn(Dispatchers.IO)

    fun addCheckIn(locationId: String, userId: String) = flow {
        dao.insert(CheckInModel(0, locationId, userId, Date()))
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}