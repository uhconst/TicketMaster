package com.uhc.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.uhc.domain.interactor.FavouriteEventsUseCase
import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository
import org.junit.Before
import org.junit.Test

class FavouriteEventsUseCaseTest {

    private val repositoryMock = mock<EventRepository>()

    private lateinit var useCase: FavouriteEventsUseCase

    @Before
    fun `Setup test`() {
        useCase = FavouriteEventsUseCase(repositoryMock)
    }

    @Test
    fun `Test updateFavouriteEvent() when is favourite and delete is called`() {
        //Prepare
        val mock = getEventMock(true)

        //Action
        useCase.updateFavouriteEvent(mock)

        //Test
        verify(repositoryMock).deleteFavouriteEvent(mock.id)
    }

    @Test
    fun `Test updateFavouriteEvent() when is NOT favourite and save is called`() {
        //Prepare
        val mock = getEventMock(false)

        //Action
        useCase.updateFavouriteEvent(mock)

        //Test
        verify(repositoryMock).saveFavouriteEvent(mock)
    }

    private fun getEventMock(favourite: Boolean): Event {
        return Event(
            "1",
            "title",
            "/image.jpg",
            "05/02/1993",
            "Hyrule",
            favourite
        )
    }
}