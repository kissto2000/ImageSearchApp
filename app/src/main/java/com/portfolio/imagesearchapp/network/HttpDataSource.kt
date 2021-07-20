package com.portfolio.imagesearchapp.network

import com.portfolio.imagesearchapp.network.model.SearchImageObj
import retrofit2.Response

interface HttpDataSource {
    suspend fun searchImage(query: String, sort: String, page: Int, size: Int): Response<SearchImageObj>
}