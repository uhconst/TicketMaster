package com.uhc.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/**
 * Returns a `LiveData` containing the results of applying the given [transform] function
 * to the value in the original `LiveData`.
 */
fun <T, R> LiveData<T>.map(transform: (T) -> R): LiveData<R> =
    Transformations.map(this, transform)