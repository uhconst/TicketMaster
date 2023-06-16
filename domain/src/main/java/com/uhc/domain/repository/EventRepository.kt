package com.uhc.domain.repository

import com.uhc.domain.model.Event
import io.reactivex.Completable
import io.reactivex.Single

interface EventRepository {
    suspend fun getEvents(size: Int): List<Event>

    fun saveFavouriteEvent(event: Event): Completable

    fun deleteFavouriteEvent(id: String): Completable

    fun getFavouritesEvent(): Single<List<Event>>
}