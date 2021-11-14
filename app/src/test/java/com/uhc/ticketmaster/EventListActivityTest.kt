package com.uhc.ticketmaster

import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.uhc.domain.model.Event
import com.uhc.presentation.event.EventListActivity
import com.uhc.presentation.event.EventRecyclerAdapter
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class EventListActivityTest : KoinTest {

    companion object {
        private const val POSITION = 0
    }

    private val observerEventsMock: Observer<List<Event>> = mock()

    private lateinit var controller: ActivityController<EventListActivity>

    @Before
    fun `Setup test`() {
        controller = Robolectric.buildActivity(EventListActivity::class.java).create()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun `Finish test`() {
        stopKoin()
        RxJavaPlugins.reset()
        controller.destroy()
    }

    @Test
    fun `Test fetchEvents() when activity is resumed`() {
        //Prepare
        val activity = controller.get()
        activity.viewModel.events.observeForever(observerEventsMock)

        //Action
        controller.resume()

        //Test
        verify(observerEventsMock).onChanged(any())
    }

    @Test
    fun `Test onClickFavouriteEvent() when success occur and favourite attribute change correctly`() {
        //Prepare
        val activity = controller.visible().get()
        val recyclerView = activity.findViewById<RecyclerView>(R.id.rv_events)
        val adapter = recyclerView.adapter as EventRecyclerAdapter
        activity.viewModel.events.observeForever(observerEventsMock)

        controller.resume() //First need to load Events into Adapter

        //Action
        val eventClicked = adapter.getEventByPosition(POSITION)
        recyclerView.findViewHolderForAdapterPosition(POSITION)
            ?.itemView
            ?.findViewById<AppCompatImageView>(R.id.iv_star)
            ?.performClick()

        //Test
        verify(
            observerEventsMock,
            times(2)
        ).onChanged(any())

        /** Get first item after got reordered favourite */
        val eventUpdated = activity.viewModel.events.value?.get(0)
        Assert.assertEquals(
            !eventClicked.favourite,
            eventUpdated?.favourite
        )
    }
}