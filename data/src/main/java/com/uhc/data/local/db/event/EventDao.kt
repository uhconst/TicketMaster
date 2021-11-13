package com.uhc.data.local.db.event

import androidx.room.*
import io.reactivex.Single

/** Database access interface for [FavouriteEventEntity]. */
@Dao
interface EventDao {

    /** Insert the [FavouriteEventEntity] into the database. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favouriteEventEntity: FavouriteEventEntity)

    /** Delete favourite event. */
    @Delete
    fun deleteFavourite(favouriteEventEntity: FavouriteEventEntity)

    /** Find favourite using the id provided. */
    @Query("SELECT * FROM favourite_event WHERE id = :id LIMIT 1")
    fun findFavourite(id: String): FavouriteEventEntity?

    /** Find all favourites. */
    @Query("SELECT * FROM favourite_event")
    fun findFavourites(): Single<List<FavouriteEventEntity>>
}