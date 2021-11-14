package com.uhc.ticketmaster

import androidx.room.Room
import com.uhc.data.local.db.AppDatabase
import org.koin.dsl.module

/** Room In memory database for tests */
val roomTestModule = module(override = true) {
    single {
        Room.inMemoryDatabaseBuilder(get(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}