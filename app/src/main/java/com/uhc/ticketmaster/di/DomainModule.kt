package com.uhc.ticketmaster.di

import com.uhc.data.repository.EventRepositoryImpl
import com.uhc.domain.interactor.FavouriteEventsUseCase
import com.uhc.domain.interactor.GetEventsUseCase
import com.uhc.domain.repository.EventRepository
import org.koin.dsl.module

val domainModule = module {

    single<EventRepository> { EventRepositoryImpl(get(), get()) }

    factory { GetEventsUseCase(get()) }

    factory { FavouriteEventsUseCase(get()) }
}