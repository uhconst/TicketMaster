package com.uhc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.uhc.domain.exception.DefaultException
import com.uhc.domain.interactor.FavouriteEventsUseCase
import com.uhc.domain.interactor.GetEventsUseCase
import com.uhc.domain.model.Event
import com.uhc.presentation.event.EventListViewModel
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class EventListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        private val MOCK_EVENTS = listOf(
            Event(
                "1",
                "title",
                "/image.jpg",
                "05/02/1993",
                "Mordor",
                true
            )
        )
    }

    private val getEventsUseCaseMock = mock<GetEventsUseCase>()

    private val favouriteEventsUseCaseMock = mock<FavouriteEventsUseCase>()

    private val observerEventsMock: Observer<List<Event>> = mock()

    private val observerErrorMock: Observer<DefaultException> = mock()

    private lateinit var viewModel: EventListViewModel

    @Before
    fun `Setup test`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = EventListViewModel(
            getEventsUseCaseMock,
            favouriteEventsUseCaseMock
        )
    }

    @Test
    fun `Test fetchEvents() when success`() {
        //Prepare
        val subjectDelay = PublishSubject.create<List<Event>>()
        Mockito.`when`(getEventsUseCaseMock.getEvents(any()))
            .thenReturn(Single.just(MOCK_EVENTS).delaySubscription(subjectDelay))

        viewModel.events.observeForever(observerEventsMock)

        //Action
        viewModel.fetchEvents()

        //Test
        `Test showLoading`(subjectDelay)
        verify(observerEventsMock).onChanged(MOCK_EVENTS)
    }

    @Test
    fun `Test fetchEvents() when error`() {
        //Prepare
        val mockException = DefaultException("Mock error message")
        Mockito.`when`(getEventsUseCaseMock.getEvents(any()))
            .thenReturn(Single.error(mockException))

        viewModel.error.observeForever(observerErrorMock)

        //Action
        viewModel.fetchEvents()

        //Test
        verify(observerErrorMock).onChanged(mockException)
    }

    private fun `Test showLoading`(subjectDelay: PublishSubject<List<Event>>) {
        MatcherAssert.assertThat(viewModel.showLoading.get(), CoreMatchers.`is`(true))

        subjectDelay.onComplete()

        MatcherAssert.assertThat(viewModel.showLoading.get(), CoreMatchers.`is`(false))
    }
}