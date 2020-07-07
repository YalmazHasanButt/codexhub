package com.funkymaster.demo.data.network

import com.funkymaster.demo.data.model.Brand
import com.funkymaster.demo.data.model.Product
import com.funkymaster.demo.data.model.UserInfo
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("customer/login")
    fun login(@Body info: UserInfo): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("customer/register")
    fun registerUser(
        @Body info: UserInfo
    ): retrofit2.Call<ResponseBody>

    @POST("catalog/brands")
    fun registerBrand(
        @Body info: Brand
    ): retrofit2.Call<ResponseBody>

    @POST("shop/products")
    fun registerProduct(
        @Body info: Product
    ): retrofit2.Call<ResponseBody>
}

class RetrofitInstance {
    companion object {
        val BASE_URL: String = "https://beta.codxhub.net/api/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}