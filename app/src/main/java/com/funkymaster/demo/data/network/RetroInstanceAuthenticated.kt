package com.funkymaster.demo.data.network

import com.funkymaster.demo.utilities.MyApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstanceAuthenticated {


    companion object {
        val BASE_URL: String = "https://beta.codxhub.net/api/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("Authorization", "Bearer "+ MyApplication.token)
                    builder.header("Content-Type", "application/json")
                    builder.header("X-Device", "Android")
                    builder.header("Accept", "application/json")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()

        fun getRetrofitAuthenticatedInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}