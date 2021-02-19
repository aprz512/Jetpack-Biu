package com.aprz.jetpack_biu.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 该类用于提供访问网络服务所需要的 Retrofit，需要注意的是返回的是单例，有特殊需要请自行提供一个
 */
class Http {


    companion object {

        @JvmStatic
        val retrofit = provideRetrofit()

        private fun provideRetrofit(): Retrofit {

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }


}