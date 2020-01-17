package by.gomeltour.repository

import by.gomeltour.dao.EventDao
import by.gomeltour.helper.launchScopeIO
import by.gomeltour.model.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EventRepo(private val dao: EventDao) {

    fun getEventsForPeriod(startDate: Long, endDate: Long): Flow<List<EventModel>> = flow {
        //launchScopeIO {
            val events = dao.getEventsForPeriod(/*startDate, endDate*/)
            emit(events)
        //}
    }.flowOn(Dispatchers.IO)

    fun upsertBatch(events: List<EventModel>): Flow<Unit> = flow {
        events.forEach {
            if (dao.insert(it) == -1L) dao.update(it)
        }
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}