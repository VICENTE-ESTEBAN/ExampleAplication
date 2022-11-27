package com.actia.myapplication.data.service

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitOmdbProvider {

    private lateinit var retrofit:Retrofit

    fun <S> createService(baseUrl:String, serviceClass: Class<S>): S {
        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(logging)

        val pool = ConnectionPool(5, 60000, TimeUnit.MILLISECONDS)
        httpClient.connectionPool(pool)

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(serviceClass)
    }
}