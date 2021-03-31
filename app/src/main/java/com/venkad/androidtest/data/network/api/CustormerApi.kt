package com.venkad.androidtest.data.network.api

import com.google.gson.JsonObject
import com.venkad.androidtest.data.network.NetworkConnectionInterceptor
import com.venkad.androidtest.data.network.model.LoginResponse
import com.venkad.androidtest.data.network.model.RegisterResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CustormerApi {

    @POST("customer/register")
    suspend fun register(
        @Body jsonObject: JsonObject
    ): Response<RegisterResponse>

    @POST("customer/login")
    suspend fun login(
        @Body jsonObject: JsonObject
    ): Response<LoginResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): CustormerApi {
            val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl(networkConnectionInterceptor.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CustormerApi::class.java)
        }
    }
}