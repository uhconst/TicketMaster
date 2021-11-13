package com.uhc.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.uhc.data.local.db.event.EventDao
import com.uhc.data.local.db.event.FavouriteEventEntity
import com.uhc.data.remote.EventService
import com.uhc.data.remote.dto.EventDto
import com.uhc.data.repository.EventRepositoryImpl
import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class EventRepositoryTest {
    private val serviceMock = mock<EventService>()

    private val daoMock = mock<EventDao>()

    private lateinit var repository: EventRepository

    private val eventId = "1"

    @Before
    fun `Setup test`() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        repository = EventRepositoryImpl(
            serviceMock,
            daoMock
        )
    }

    @Test
    fun `Test getEvents() when event id is NOT found in favourite`() {
        //Prepare
        Mockito.`when`(serviceMock.getEvents(any()))
            .thenReturn(Single.just(getResponse()))

        Mockito.`when`(daoMock.findFavourite(eventId))
            .thenReturn(null)

        //Action
        val testObserver = repository.getEvents(any()).test()

        //Test
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(getEventMock(false)))
    }

    @Test
    fun `Test getEvents() when event id is found in favourite`() {
        //Prepare
        Mockito.`when`(serviceMock.getEvents(any()))
            .thenReturn(Single.just(getResponse()))

        Mockito.`when`(daoMock.findFavourite(eventId))
            .thenReturn(FavouriteEventEntity(eventId))

        //Action
        val testObserver = repository.getEvents(any()).test()

        //Test
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(getEventMock(true)))
    }

    private fun getEventMock(favourite: Boolean): Event =
        Event(
            eventId,
            "name",
            "/image.jpg",
            "05/02/1993",
            "National Museum",
            favourite
        )

    private fun getResponse(): EventDto.Response =
        EventDto.Response(
            EventDto.EmbeddedResponse(
                listOf(
                    getEventResponseMock()
                )
            )
        )

    private fun getEventResponseMock(): EventDto.EventResponse =
        EventDto.EventResponse(
            eventId,
            "name",
            getDatesResponseMock(),
            getImageResponseMock(),
            getEmbeddedVenuesResponseMock()
        )

    private fun getDatesResponseMock(): EventDto.DatesResponse =
        EventDto.DatesResponse(
            getStartResponseMock()
        )

    private fun getStartResponseMock(): EventDto.StartResponse =
        EventDto.StartResponse(
            "05/02/1993"
        )


    private fun getImageResponseMock(): List<EventDto.ImageResponse> =
        listOf(
            EventDto.ImageResponse(
                "/image.jpg"
            )
        )

    private fun getEmbeddedVenuesResponseMock(): EventDto.EmbeddedVenuesResponse =
        EventDto.EmbeddedVenuesResponse(
            listOf(
                EventDto.VenueResponse(
                    "National Museum"
                )
            )
        )
}