package com.uhc.ticketmaster

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uhc.data.local.db.event.EventDao
import com.uhc.data.local.db.event.FavouriteEventEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject


/**
 * EventDaoTest is a KoinTest with AndroidJUnit4 runner
 *
 * KoinTest help inject Koin components from actual runtime
 */
@RunWith(AndroidJUnit4::class)
class EventDaoTest : KoinTest {

    /**
     * Inject EventDao from Koin
     */
    private val eventDao: EventDao by inject()

    /**
     * Override default Koin configuration to use Room in-memory database
     */
    @Before
    fun before() {
        loadKoinModules(roomTestModule)
    }

    @Test
    fun testInsertFavouriteAndFindFavourites() {
        //Prepare
        val entity1 = getFavouriteEventEntityMock()
        val entity2 = getFavouriteEventEntityMock("2")

        //Action
        eventDao.insertFavourite(entity1)
        eventDao.insertFavourite(entity2)

        val allFavourites = eventDao.findFavourites().blockingGet()

        //Test
        assertEquals(2, allFavourites.size)
    }

    @Test
    fun testFindFavourite() {
        //Prepare
        val entity = getFavouriteEventEntityMock()

        //Action
        eventDao.insertFavourite(entity)

        val favouriteById = eventDao.findFavourite(entity.id)

        //Test
        assertEquals(entity, favouriteById)
    }


    @Test
    fun testDeleteFavourite() {
        //Prepare
        val entity = getFavouriteEventEntityMock("2")

        //Action
        eventDao.insertFavourite(entity)
        eventDao.deleteFavourite(entity)
        val favouriteById = eventDao.findFavourite(entity.id)

        //Test
        assertNull(favouriteById)
    }

    private fun getFavouriteEventEntityMock(id: String = "1"): FavouriteEventEntity =
        FavouriteEventEntity(id)
}