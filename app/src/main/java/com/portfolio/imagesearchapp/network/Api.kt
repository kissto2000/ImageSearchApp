package com.portfolio.imagesearchapp.network

import com.portfolio.imagesearchapp.Const
import com.portfolio.imagesearchapp.network.model.SearchImageObj
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(Const.KAKAO.SEARCH_IMAGE)
    suspend fun getSearchImage(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchImageObj>
}