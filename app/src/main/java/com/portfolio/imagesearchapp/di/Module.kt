package com.portfolio.imagesearchapp.di

import com.portfolio.imagesearchapp.network.HttpHelper
import com.portfolio.imagesearchapp.ui.main.MainViewModel
import com.portfolio.imagesearchapp.ui.photo.PhotoViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val retrofit = module {
    single { HttpHelper() }
}

val viewModel = module {
    viewModel { MainViewModel(get()) }
    viewModel { PhotoViewModel() }
}

var allModule = listOf(retrofit, viewModel)