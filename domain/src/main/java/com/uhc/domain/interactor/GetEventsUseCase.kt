package com.uhc.domain.interactor

import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository
import io.reactivex.Single

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {

    fun getEvents(size: Int): Single<List<Event>> =
        eventRepository.getEvents(size)

}