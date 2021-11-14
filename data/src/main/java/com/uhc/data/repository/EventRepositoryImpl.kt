package com.uhc.data.repository

import com.uhc.data.local.db.event.EventDao
import com.uhc.data.local.db.event.FavouriteEventEntity
import com.uhc.data.remote.EventService
import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Event Repository Implementation
 */
class EventRepositoryImpl(
    private val service: EventService,
    private val dao: EventDao
) : EventRepository {

    override fun getEvents(size: Int): Single<List<Event>> =
        service.getEvents(size)
            .subscribeOn(Schedulers.io())
            .map { it.embedded.events }
            .flattenAsObservable { it }
            .map {
                /**
                 * For each EventDto.Response verify if
                 * it's a favourite event in FavouriteEventEntity by id
                 */
                val isFavourite = dao.findFavourite(it.id) != null
                it.toEvent(isFavourite)
            }
            .toList()


    override fun saveFavouriteEvent(event: Event): Completable =
        Completable.create {
            dao.insertFavourite(FavouriteEventEntity(event.id))
            it.onComplete()
        }.subscribeOn(Schedulers.io())


    override fun deleteFavouriteEvent(id: String): Completable =
        Completable.create {
            dao.deleteFavourite(FavouriteEventEntity(id))
            it.onComplete()
        }.subscribeOn(Schedulers.io())


    override fun getFavouritesEvent(): Single<List<Event>> =
        dao.findFavourites()
            .subscribeOn(Schedulers.io())
            .flattenAsObservable { it }
            .flatMapSingle { entity ->
                service.getEventById(entity.id)
                    .map { it.toEvent(true) }
            }
            .toList()
            .map { it.filterNotNull() }

}