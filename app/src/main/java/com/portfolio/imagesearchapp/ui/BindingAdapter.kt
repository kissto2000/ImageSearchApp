package com.portfolio.imagesearchapp.ui

import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.portfolio.imagesearchapp.interfaces.OnPositionListener
import com.portfolio.imagesearchapp.network.model.SearchImageObj
import com.portfolio.imagesearchapp.ui.main.MainAdapter

@BindingAdapter(value = ["items", "listener"])
fun setMainAdapter(
    view: RecyclerView,
    items: ObservableArrayList<SearchImageObj.Document>,
    listener: OnPositionListener
) {
    view.adapter?.run {
        if (this is MainAdapter) {
            this.items = items
            this.listener = listener
            this.notifyDataSetChanged()
        }
    } ?: run {
        MainAdapter(items, listener).apply {
            view.adapter = this
        }
    }
}

@BindingAdapter(value = ["textChangedListener"])
fun setTextWatcher(
    editText: EditText,
    textWatcher: TextWatcher
) {
    editText.addTextChangedListener(textWatcher)
}

@BindingAdapter("imageUrl", "listener")
fun setLoadImage(
    imageView: ImageView,
    url: String?,
    listener: RequestListener<Drawable>
) {
    url?.let {
        Glide.with(imageView.context)
            .load(it)
            .listener(listener)
            .into(imageView)
    }
}