package com.uhc.domain.interactor

import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {
    suspend fun getEvents(size: Int): List<Event> = eventRepository.getEvents(size)
}