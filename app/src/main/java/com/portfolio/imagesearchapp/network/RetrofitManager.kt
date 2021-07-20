package com.portfolio.imagesearchapp.network

import android.annotation.SuppressLint
import com.portfolio.imagesearchapp.BuildConfig
import com.portfolio.imagesearchapp.Const
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

open class RetrofitManager(private val url: String,
                           private val type: String?) {
    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    init{
        /*
         * 로깅 인터셉터 연결
         */
        val httpLogging = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
//            httpLogging.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            httpLogging.level = HttpLoggingInterceptor.Level.NONE
//        }

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
            addInterceptor(HeaderSettingInterceptor(type))
            connectTimeout(Const.HTTP_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Const.HTTP_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Const.HTTP_TIMEOUT, TimeUnit.SECONDS)

            sslSocketFactory(getSslContext().socketFactory, getX509TrustManager())
            hostnameVerifier { _, _ -> true }
        }.build()

        /*
         * Retrofit2 + OKHttp3를 연결한다
         */
        retrofit = Retrofit.Builder().apply{
            baseUrl(url)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create()) //gson을 이용해 json파싱
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build()
    }

    @SuppressLint("TrustAllX509TrustManager")
    private fun getX509TrustManager() : X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> { return arrayOf() }
        }
    }

    private fun getSslContext() : SSLContext {
        val trustManagers = arrayOf<TrustManager>(getX509TrustManager())
        return  SSLContext.getInstance("TLS").apply {
            init(null, trustManagers, null)
        }
    }

    /**
     *  Request Header를 세팅하는 Interceptor
     */
    private class HeaderSettingInterceptor(private val type: String?) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val chainRequest = chain.request()

            val request = chainRequest.newBuilder().apply{
                addHeader("Accept", "application/json")
                if (!type.isNullOrEmpty()) addHeader("Authorization", "KakaoAK 99c30770e97f8f3d42a05662e320f5f7")
//                addHeader("Content-Type", "application/json; charset=utf-8")
//                addHeader("appKey", SK_API_KEY)
            }.build()

            return chain.proceed(request)
        }
    }

    /**
     * 현재 선언된 요청 인터페이스 객체(RetrofitInterface)를 리턴한다
     */
    internal fun <T> getRetrofitService(restClass: Class<T>): T {
        return retrofit.create(restClass)
    }
}