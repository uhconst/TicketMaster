package com.uhc.data.local.db.event

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single Event.
 *
 * @property id Unique identifier of the favourite event in the DB.
 */
@Entity(tableName = "favourite_event")
data class FavouriteEventEntity(
    @PrimaryKey val id: String
)