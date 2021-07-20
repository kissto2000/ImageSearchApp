package com.portfolio.imagesearchapp.network

import android.annotation.SuppressLint
import com.portfolio.imagesearchapp.Const
import com.portfolio.imagesearchapp.network.model.SearchImageObj
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class HttpHelper : HttpDataSource {

    private var kakaoApi : Api = RetrofitManager(Const.KAKAO.URL, "K").getRetrofitService(Api::class.java)

    /*****************************************************************************
     * API 목록
     *****************************************************************************/
    override suspend fun searchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<SearchImageObj> {
        return kakaoApi.getSearchImage(query, sort, page, size)
    }
}