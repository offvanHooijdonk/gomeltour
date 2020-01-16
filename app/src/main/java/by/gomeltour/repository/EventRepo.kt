package by.gomeltour.repository

import by.gomeltour.dao.EventDao
import by.gomeltour.helper.launchScopeIO
import by.gomeltour.model.EventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepo(private val dao: EventDao) {

    fun getEventsForPeriod(startDate: Long, endDate: Long): Flow<List<EventModel>> = flow {
        emit(dao.getEventsForPeriod(startDate, endDate))
    }

    fun upsertBatch(events: List<EventModel>): Flow<Unit> = flow {
        events.forEach {
            if (dao.insert(it) == -1L) dao.update(it)
        }
        emit(Unit)
    }
}