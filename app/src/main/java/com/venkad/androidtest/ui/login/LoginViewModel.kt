package com.venkad.androidtest.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.venkad.androidtest.data.network.Coroutines
import com.venkad.androidtest.data.repository.CustomerRepository
import com.venkad.androidtest.util.Constants
import com.venkad.androidtest.util.NoInternetException
import khangtran.preferenceshelper.PrefHelper

class LoginViewModel(
    private val customerRepository: CustomerRepository
) :ViewModel() {
    var loginCallBack: LoginCallBack? = null

    var emailId: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun login(emailId: String, password: String) {
        loginCallBack?.onStarted()
        try {
            Coroutines.main {
                val response = customerRepository.login(emailId, password)

                if (response.value != null) {
                    PrefHelper.setVal(Constants.KEY_TOKEN, response.value!!.data.token)
                    loginCallBack?.onSuccess(response.value!!.message)
                } else {
                    loginCallBack?.onError("Login failed")
                }
            }
        } catch (e: NoInternetException) {
            loginCallBack?.onError(e.message!!)
        }
    }
}