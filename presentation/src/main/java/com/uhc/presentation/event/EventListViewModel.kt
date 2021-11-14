package com.uhc.presentation.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uhc.domain.exception.DefaultException
import com.uhc.domain.interactor.FavouriteEventsUseCase
import com.uhc.domain.interactor.GetEventsUseCase
import com.uhc.domain.model.Event
import com.uhc.presentation.ui.base.BaseViewModel
import com.uhc.presentation.utils.SingleLiveData
import com.uhc.presentation.utils.map

class EventListViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val favouriteEventsUseCase: FavouriteEventsUseCase
) : BaseViewModel() {

    sealed class State {
        object GoToFavourite : State()
    }

    private var size: Int = 50

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events.map {
        it.sortedByDescending { event ->
            event.favourite
        }
    }

    val error = SingleLiveData<DefaultException>()

    val state = SingleLiveData<State>()

    fun fetchEvents() {
        subscribeSingle(
            observable = getEventsUseCase.getEvents(size)
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = {
                _events.postValue(it)
            },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavouriteEvent(event: Event) {
        subscribeCompletable(
            observable = favouriteEventsUseCase.updateFavouriteEvent(event),
            complete = { fetchEvents() },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavourite() {
        state.value = State.GoToFavourite
    }

}