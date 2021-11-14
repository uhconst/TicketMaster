package com.uhc.ticketmaster

import androidx.test.core.app.ApplicationProvider
import com.uhc.presentation.event.EventListViewModel
import com.uhc.ticketmaster.di.appModule
import com.uhc.ticketmaster.di.dataModule
import com.uhc.ticketmaster.di.domainModule
import com.uhc.ticketmaster.di.presentationModule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TicketMasterApp::class)
class ModuleCheckTest : KoinTest {

    private val viewModelId = "someId"

    @Test
    fun testRemoteConfiguration() {
        koinApplication {
            androidContext(ApplicationProvider.getApplicationContext<TicketMasterApp>())
            modules(
                appModule,
                dataModule,
                domainModule
//                presentationModule
            )
        }.checkModules {
            create<EventListViewModel> { parametersOf(viewModelId) }
        }
    }
}