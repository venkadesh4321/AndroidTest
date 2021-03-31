package com.venkad.androidtest.ui.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.venkad.androidtest.data.network.Coroutines
import com.venkad.androidtest.data.network.model.Products
import com.venkad.androidtest.data.repository.CustomerRepository
import com.venkad.androidtest.data.repository.ProductRepository
import com.venkad.androidtest.ui.login.LoginCallBack
import com.venkad.androidtest.util.NoInternetException

class ProductsViewModel(
    private val productRepository: ProductRepository
): ViewModel() {
    var productsCallBack: ProductCallBack? = null

    private val _products = MutableLiveData<ArrayList<Products>>()
    val products: LiveData<ArrayList<Products>>
        get() = _products

    var emailId: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun getProducts() {
        productsCallBack?.onStarted()
        try {
            Coroutines.main {
                val response = productRepository.getProducts()

                if (response.value != null) {
                    _products.value = response.value!!.data
                    productsCallBack?.onSuccess("success")
                }
            }
        } catch (e: NoInternetException) {
            productsCallBack?.onError(e.message!!)
        }
    }
}