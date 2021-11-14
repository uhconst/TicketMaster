package com.uhc.domain.interactor

import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository
import io.reactivex.Completable
import io.reactivex.Single

class FavouriteEventsUseCase(
    private val eventRepository: EventRepository
) {

    fun updateFavouriteEvent(event: Event): Completable {
        return if (event.favourite) {
            deleteFavouriteEvent(event)
        } else {
            saveFavouriteEvent(event)
        }
    }

    private fun saveFavouriteEvent(event: Event): Completable {
        return eventRepository.saveFavouriteEvent(event)
    }

    private fun deleteFavouriteEvent(event: Event): Completable {
        return eventRepository.deleteFavouriteEvent(event.id)
    }

    fun getFavouriteEvents(): Single<List<Event>> {
        return eventRepository.getFavouritesEvent()
    }
}