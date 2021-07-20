package com.portfolio.imagesearchapp.ui.main

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.portfolio.imagesearchapp.Const
import com.portfolio.imagesearchapp.base.BaseViewModel
import com.portfolio.imagesearchapp.interfaces.OnPositionListener
import com.portfolio.imagesearchapp.livedata.SingleLiveEvent
import com.portfolio.imagesearchapp.network.HttpHelper
import com.portfolio.imagesearchapp.network.model.SearchImageObj
import kotlinx.coroutines.*

class MainViewModel(
    var httpHelper: HttpHelper
) : BaseViewModel() {

    private val _document = SingleLiveEvent<SearchImageObj.Document>()
    val document: LiveData<SearchImageObj.Document> get() = _document
    private val _editClear = SingleLiveEvent<Any>()
    val editClear: LiveData<Any> get() = _editClear

    val searchImages = ObservableArrayList<SearchImageObj.Document>()
    val isEmptyVisible = ObservableInt().apply { View.VISIBLE }

    var query: String = ""
    var sort: String = Const.KAKAO.SORT.ACCURACY
    var page: Int = 1
    var size: Int = Const.KAKAO.SORT.SIZE

    var isContinue: Boolean = true
    var isEnd: Boolean = false

    val handler = object : Handler(Looper.myLooper() ?: Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                Const.HANDLER.SEARCH_IMAGE -> {
                    val text = msg.obj as String
                    page = 1
                    getSearchImage(text, page)
                }
            }
        }
    }

    var onTextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            searchImages.clear()
            handler.removeMessages(Const.HANDLER.SEARCH_IMAGE)
            isEmptyVisible.set(View.VISIBLE)
            s?.let {
                val text = it.toString()
                handler.sendMessageDelayed(Message().apply {
                    what = Const.HANDLER.SEARCH_IMAGE
                    obj = text
                }, 1000)
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
    }

    var onPositionListener = object : OnPositionListener {
        override fun onPosition(position: Int) {
            _document.value = searchImages[position]
        }
    }

    fun getSearchImage(text: String, page: Int) {
        query = text

        viewModelScope.launch(ioDispatchers) {
            val response = httpHelper.searchImage(query, sort, page, size)
            if (response.isSuccessful) {
                val requestQuery = response.raw().request().url().queryParameter("query")
                if (requestQuery != query) {
                    searchImages.clear()
                    return@launch
                }

                response.body()?.let {
                    isContinue = true
                    isEnd = it.meta.is_end

                    withContext(uiDispatchers) {
                        searchImages.addAll(it.documents)

                        if (page == 1 && searchImages.size == 0) {
                            isEmptyVisible.set(View.VISIBLE)
                        } else {
                            isEmptyVisible.set(View.GONE)
                        }
                    }
                } ?: run {
                    withContext(uiDispatchers) {
                        isEmptyVisible.set(View.VISIBLE)
                    }
                }
            }
        }
    }

    fun onCancelListener() {
        _editClear.call()
    }
}