package com.venkad.androidtest.ui.products

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venkad.androidtest.R
import com.venkad.androidtest.adapter.ProductAdapter
import com.venkad.androidtest.databinding.ActivityProductsBinding
import com.venkad.androidtest.ui.login.LoginActivity
import khangtran.preferenceshelper.PrefHelper
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProductsActivity : AppCompatActivity(), KodeinAware, ProductCallBack {
    // injection
    override val kodein by kodein()

    lateinit var activityProductsBinding: ActivityProductsBinding
    lateinit var productsViewModel: ProductsViewModel
    private val productsViewModelFactory: ProductsViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
        setToolBar()
        initViewModel()

        // products observer
        productsViewModel.products.observe(this, { response ->
            activityProductsBinding.productRecyclerView.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = ProductAdapter(response)
            }
        })

        productsViewModel.getProducts()
    }

    private fun setToolBar() {
        setSupportActionBar(activityProductsBinding.toolbar)
        activityProductsBinding.toolbar.title = "Products"
    }

    private fun bindViews() {
        activityProductsBinding = DataBindingUtil.setContentView(this, R.layout.activity_products)
    }

    private fun initViewModel() {
        productsViewModel = ViewModelProvider(this, productsViewModelFactory).get(ProductsViewModel::class.java)
        productsViewModel.productsCallBack = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_product, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                PrefHelper.removeAllKeys()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStarted() {
        Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(message: String) {

    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

interface ProductCallBack {
    fun onStarted()
    fun onSuccess(message: String)
    fun onError(message: String)
}