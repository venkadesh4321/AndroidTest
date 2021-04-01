package com.venkad.androidtest.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.venkad.androidtest.data.db.TestDatabase
import com.venkad.androidtest.data.network.model.Products
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DbRepository {

    companion object {

        var loginDatabase: TestDatabase? = null

        fun initializeDB(context: Context) : TestDatabase {
            return TestDatabase.getDatabaseClient(context)
        }

        fun insertData(context: Context, products: ArrayList<Products>) {

            loginDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                loginDatabase!!.getProductsDao().insertProducts(products)
            }
        }
/*
        fun getProducts(context: Context, id: Int) : ArrayList<Products> {

            loginDatabase = initializeDB(context)
            var products: ArrayList<Products>? = null

            CoroutineScope(IO).launch {
                products = loginDatabase!!.getProductsDao().getProducts(id)
            }
            return  products!!
        }*/

    }
}