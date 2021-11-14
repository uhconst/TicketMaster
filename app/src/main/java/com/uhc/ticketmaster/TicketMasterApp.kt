package com.uhc.ticketmaster

import android.app.Application
import com.uhc.ticketmaster.di.appModule
import com.uhc.ticketmaster.di.dataModule
import com.uhc.ticketmaster.di.domainModule
import com.uhc.ticketmaster.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TicketMasterApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TicketMasterApp)
            modules(
                appModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}