package com.venkad.androidtest.ui.products

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.venkad.androidtest.data.network.Coroutines
import com.venkad.androidtest.data.network.model.Products
import com.venkad.androidtest.data.repository.DbRepository
import com.venkad.androidtest.data.repository.ProductRepository
import com.venkad.androidtest.util.Constants
import com.venkad.androidtest.util.NoInternetException
import khangtran.preferenceshelper.PrefHelper

class ProductsViewModel(
        private val productRepository: ProductRepository
) : ViewModel() {
    var productsCallBack: ProductCallBack? = null

    private val _products = MutableLiveData<ArrayList<Products>>()
    val products: LiveData<ArrayList<Products>>
        get() = _products

    fun getProducts(context: Context) {
        productsCallBack?.onStarted()
        try {
            Coroutines.main {
                var token = PrefHelper.getStringVal(Constants.KEY_TOKEN)
                val shopId = PrefHelper.getIntVal(Constants.KEY_SHOP_ID)
                token = "Bearer $token"
                val response = productRepository.getProducts(token, shopId)

                if (response.value != null) {
                    _products.value = response.value!!.data
                    saveProduct(context, response.value!!.data)
                    productsCallBack?.onSuccess(response)
                }
            }
        } catch (e: NoInternetException) {
            productsCallBack?.onError(e.message!!)
        }
    }

    fun saveProduct(context: Context, products: ArrayList<Products>) {
        DbRepository.insertData(context, products)
    }

    /*fun getProductFromLocal(context: Context, id: Int): ArrayList<Products> {
        DbRepository.getProducts(context, id)
    }*/
}