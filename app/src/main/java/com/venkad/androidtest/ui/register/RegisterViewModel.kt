package com.venkad.androidtest.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.venkad.androidtest.data.network.Coroutines
import com.venkad.androidtest.data.repository.CustomerRepository
import com.venkad.androidtest.ui.login.LoginCallBack
import com.venkad.androidtest.util.Constants
import com.venkad.androidtest.util.NoInternetException
import khangtran.preferenceshelper.PrefHelper

class RegisterViewModel(
    private val customerRepository: CustomerRepository
): ViewModel() {
    var registerCallBack: RegisterCallBack? = null

    var emailId: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun register() {
        registerCallBack?.onStarted()
        try {
            Coroutines.main {
                val response = customerRepository.register(emailId.value!!, password.value!!)

                if (response.value != null) {
                    PrefHelper.setVal(Constants.KEY_TOKEN, response.value!!.data.token)
                    registerCallBack?.onSuccess("success")
                } else {
                    registerCallBack?.onError("Registration failed")
                }
            }
        } catch (e: NoInternetException) {
            registerCallBack?.onError(e.message!!)
        }
    }
}