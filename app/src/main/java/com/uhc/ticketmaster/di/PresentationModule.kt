package com.uhc.ticketmaster.di

import com.uhc.presentation.event.EventListViewModel
import com.uhc.presentation.event.EventRecyclerAdapter
import com.uhc.presentation.event.favourite.FavouriteEventListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { EventListViewModel(get(), get()) }

    viewModel { FavouriteEventListViewModel(get()) }

    factory { EventRecyclerAdapter() }
}