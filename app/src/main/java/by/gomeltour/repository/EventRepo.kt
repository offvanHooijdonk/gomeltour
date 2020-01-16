package by.gomeltour.repository

import by.gomeltour.dao.EventDao
import by.gomeltour.model.EventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepo(private val eventDao: EventDao) {
    fun getEventsForPeriod(startDate: Long, endDate: Long?): Flow<List<EventModel>> = flow {

    }

    fun upsertBatch(events: List<EventModel>): Flow<Unit> = flow {
        events.forEach {
            if (eventDao.insert(it) == -1L) eventDao.update(it)
        }
        emit(Unit)
    }
}