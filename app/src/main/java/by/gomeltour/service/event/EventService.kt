package by.gomeltour.service.event

import by.gomeltour.model.EventModel
import by.gomeltour.repository.EventRepo
import kotlinx.coroutines.flow.Flow

class EventService(private val eventRepo: EventRepo) {
    fun getEventsForPeriod(startDate: Long, endDate: Long): Flow<List<EventModel>> = eventRepo.getEventsForPeriod(startDate, endDate)
}