package com.portfolio.imagesearchapp.ui.photo

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.portfolio.imagesearchapp.base.BaseViewModel
import com.portfolio.imagesearchapp.network.model.SearchImageObj

class PhotoViewModel : BaseViewModel() {

    val document = ObservableField<SearchImageObj.Document>()
    val isProgressVisible = ObservableBoolean().apply { set(true) }

    override fun create() {

    }

    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            isProgressVisible.set(false)
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            isProgressVisible.set(false)
            return false
        }

    }
}