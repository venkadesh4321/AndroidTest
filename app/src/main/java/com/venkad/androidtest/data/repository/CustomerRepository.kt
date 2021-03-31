package com.venkad.androidtest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.venkad.androidtest.data.network.SafeApiRequest
import com.venkad.androidtest.data.network.api.CustormerApi
import com.venkad.androidtest.data.network.model.LoginResponse
import com.venkad.androidtest.data.network.model.RegisterResponse

class CustomerRepository(
    private val customerApi: CustormerApi
) : SafeApiRequest() {

    suspend fun register(emailId: String, password: String): LiveData<RegisterResponse> {
        val response = MutableLiveData<RegisterResponse>()

        val jsonObject = JsonObject()
        jsonObject.addProperty("email_id", emailId)
        jsonObject.addProperty("password", password)

        try {
            response.value = apiRequest { customerApi.register(jsonObject) }
        } catch (e: Exception) {
            response.value = null
        }
        return response
    }

    suspend fun login(emailId: String, password: String): LiveData<LoginResponse> {
        val response = MutableLiveData<LoginResponse>()

        val jsonObject = JsonObject()
        jsonObject.addProperty("email", emailId)
        jsonObject.addProperty("password", password)

        try {
            response.value = apiRequest { customerApi.login(jsonObject) }
        } catch (e: Exception) {
            response.value = null
        }
        return response
    }
}