package by.gomeltour.repository

import by.gomeltour.dao.LocationDao
import by.gomeltour.model.LocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocationRepo(private val dao: LocationDao) {

    fun listAll(): Flow<List<LocationModel>> = flow {
        Thread.sleep(1000)
        emit(dao.listAll())
    }.flowOn(Dispatchers.IO)

    fun upsertBatch(list: List<LocationModel>): Flow<Unit> = flow {
        list.forEach {
            if (dao.insert(it) == -1L) dao.update(it)
        }
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}