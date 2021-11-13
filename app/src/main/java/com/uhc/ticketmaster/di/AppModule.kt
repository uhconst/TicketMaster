package com.uhc.ticketmaster.di

import com.google.gson.Gson
import org.koin.dsl.module

val appModule = module {

    /** Gson */
    single { Gson() }

}