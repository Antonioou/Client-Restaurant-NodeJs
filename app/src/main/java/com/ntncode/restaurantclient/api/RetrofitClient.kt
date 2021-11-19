package com.ntncode.restaurantclient.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    var interceptor = HttpLoggingInterceptor { message -> Log.e("", message) }

    private var client = OkHttpClient.Builder()
        .connectTimeout(45, TimeUnit.SECONDS) //5->10->15 다
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()


    // User 서버 연결
    private val retrofitUser = Retrofit.Builder()
        .baseUrl("http://192.168.0.200:3000")
        .client(client) //okhttp
        .addConverterFactory(GsonConverterFactory.create())

    val serviceApiUser: ApiInterface by lazy {
        retrofitUser.build().create(ApiInterface::class.java)
    }


}