package com.portfolio.imagesearchapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.portfolio.imagesearchapp.livedata.SingleLiveEvent

class Toast : SingleLiveEvent<String>() {
    fun observe(owner: LifecycleOwner, observer: (String) -> Unit) {
        super.observe(owner, Observer {
            it?.run {
                observer(it)
            }
        })
    }
}