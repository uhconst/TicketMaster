package com.uhc.ticketmaster.di

import com.uhc.data.local.db.AppDatabase
import com.uhc.data.remote.EventService
import com.uhc.data.remote.interceptor.RemoteRequestInterceptor
import com.uhc.data.remote.interceptor.RxRemoteErrorInterceptor
import com.uhc.ticketmaster.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    factory { RxRemoteErrorInterceptor() }

    factory { RemoteRequestInterceptor(BuildConfig.API_KEY) }

    single {
        AppDatabase.createDatabase(
            androidApplication(),
            "AppDatabase"
        )
    }

    single { get<AppDatabase>().eventDao() }

    single {
        EventService.createEventService(
            BuildConfig.API_URL,
            get(),
            get()
        )
    }
}