package com.venkad.androidtest.data.network.api

import com.google.gson.JsonObject
import com.venkad.androidtest.data.network.NetworkConnectionInterceptor
import com.venkad.androidtest.data.network.model.LoginResponse
import com.venkad.androidtest.data.network.model.Product
import com.venkad.androidtest.data.network.model.RegisterResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ProductApi {

    @Headers("Content-Type: application/x-www-form-urlencoded", "Accept:application/json")
    @GET("shop/products")
    suspend fun getProducts(
        @Header("X-Shop") shopId: Int, @Header("X-Device") deviceId: String, @Header("X-Branch") branch: Int, @Header("Authorization") authHeader: String
    ): Response<Product>


    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): ProductApi {
            val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl(networkConnectionInterceptor.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductApi::class.java)
        }
    }
}