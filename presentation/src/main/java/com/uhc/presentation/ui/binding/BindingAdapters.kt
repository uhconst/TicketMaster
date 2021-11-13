package com.uhc.presentation.ui.binding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.uhc.presentation.ui.extensions.loadImageUrl

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("show")
    fun View.show(show: Boolean) {
        visibility = if (show) VISIBLE else GONE
    }

    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun AppCompatImageView.loadUrl(imageUrl: String) {
        this.loadImageUrl(imageUrl)
    }


}