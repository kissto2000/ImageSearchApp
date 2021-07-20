package com.portfolio.imagesearchapp.base

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.portfolio.imagesearchapp.Toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

open class BaseViewModel : ViewModel(), BaseViewModelInit {
    /*****************************************************************************
     * Override
     *****************************************************************************/
    override fun create() {}

    /*****************************************************************************
     * Functions
     *****************************************************************************/

    /**
     * CoroutineScope 내부 Exception 처리 Handler
     */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    /**
     * Dispatchers 선언 (Normal Dispatchers + CoroutineExceptionHandler)
     */
    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHandler
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHandler

    /*****************************************************************************
     * CompositeDisposable
     *****************************************************************************/
    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    /*****************************************************************************
     * Logger
     *****************************************************************************/
    fun log(msg: String) {
        Log.i(javaClass.simpleName, msg)
    }
}