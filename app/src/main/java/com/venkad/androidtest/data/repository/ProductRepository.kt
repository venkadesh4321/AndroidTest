package com.venkad.androidtest.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.venkad.androidtest.data.network.SafeApiRequest
import com.venkad.androidtest.data.network.api.CustormerApi
import com.venkad.androidtest.data.network.api.ProductApi
import com.venkad.androidtest.data.network.model.LoginResponse
import com.venkad.androidtest.data.network.model.Product
import com.venkad.androidtest.data.network.model.RegisterResponse
import com.venkad.androidtest.util.Constants
import khangtran.preferenceshelper.PrefHelper

class ProductRepository(
    private val productApi: ProductApi
) : SafeApiRequest() {

    suspend fun getProducts(): LiveData<Product> {
        val response = MutableLiveData<Product>()

        val token = PrefHelper.getStringVal(Constants.KEY_TOKEN)
        Log.e("sss", "Bearer $token" )
        try {
            response.value = apiRequest { productApi.getProducts(46, "Android 10", 9, "Bearer $token") }
        } catch (e: Exception) {
            response.value = null
        }
        return response
    }
}