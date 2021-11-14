package com.uhc.presentation.event.favourite

import androidx.lifecycle.MutableLiveData
import com.uhc.domain.exception.DefaultException
import com.uhc.domain.interactor.FavouriteEventsUseCase
import com.uhc.domain.model.Event
import com.uhc.presentation.ui.base.BaseViewModel
import com.uhc.presentation.utils.SingleLiveData


class FavouriteEventListViewModel(
    private val favouriteEventsUseCase: FavouriteEventsUseCase
) : BaseViewModel() {

    val favouriteEvents = MutableLiveData<List<Event>>()

    val error = SingleLiveData<DefaultException>()

    fun fetchFavouriteEvents() {
        subscribeSingle(
            observable = favouriteEventsUseCase.getFavouriteEvents()
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = { favouriteEvents.postValue(it) },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavouriteEvent(event: Event) {
        subscribeCompletable(
            observable = favouriteEventsUseCase.updateFavouriteEvent(event),
            complete = { fetchFavouriteEvents() },
            error = { error.postValue(it) }
        )
    }

}