package com.uhc.ticketmaster.di

import com.uhc.presentation.event.EventListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { EventListViewModel(get(), get()) }
}