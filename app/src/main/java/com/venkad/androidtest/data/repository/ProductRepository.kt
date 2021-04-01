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

    suspend fun getProducts(token: String, shopId: Int): MutableLiveData<Product> {
        val response = MutableLiveData<Product>()

        try {
            response.value = apiRequest { productApi.getProducts(shopId, "Android 10", 9, token) }
        } catch (e: Exception) {
            response.value = null
        }
        return response
    }
}