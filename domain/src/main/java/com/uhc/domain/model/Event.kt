package com.uhc.domain.model

/**
 * Represent a single Event.
 */
data class Event(
    val id: String,
    val name: String,
    val imageUrl: String,
    val dates: String,
    val venue: String,
    val favourite: Boolean
)