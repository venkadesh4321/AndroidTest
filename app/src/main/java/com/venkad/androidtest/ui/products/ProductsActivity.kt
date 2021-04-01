package com.venkad.androidtest.ui.products

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venkad.androidtest.R
import com.venkad.androidtest.adapter.ProductAdapter
import com.venkad.androidtest.data.network.model.Product
import com.venkad.androidtest.data.network.model.Products
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
    var products: ArrayList<Products> = ArrayList()
    var productsData: ArrayList<Products> = ArrayList()
    private var productAdapter: ProductAdapter? = null
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
        setToolBar()
        initViewModel()
        initRecycler()
        initScrollListener()


        // products observer
        /*productsViewModel.products.observe(this, { response ->
            products = response
            productsData = response
            populateData()
        })*/


        productsViewModel.getProducts(this)
    }

    private fun initRecycler() {
        products = ArrayList()
        productAdapter = ProductAdapter(products)
        activityProductsBinding.productRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        activityProductsBinding.productRecyclerView.layoutManager = layoutManager
        activityProductsBinding.productRecyclerView.adapter = productAdapter
    }


    private fun initScrollListener() {
        activityProductsBinding.productRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                Log.e("sss", "onScrolled: "+isLoading )
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == products.size - 1) {
                    //bottom of list!
                    // loadMore()
                    fetchMore()

                    isLoading = true
                }
            /*if (!isLoading) {

                }*/
            }
        })
    }

    private fun fetchMore() {
        Toast.makeText(this, "fetching...", Toast.LENGTH_SHORT).show()
        var i = products.size
        var limit = i+5
        Log.e("sss", "fetchMore: "+i )
        Log.e("sss", "fetchMore: "+limit )
        while (i < limit) {
            products.add(productsData.get(i))
            i++
        }
        isLoading = false
        productAdapter!!.notifyDataSetChanged()
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

    override fun onSuccess(response: MutableLiveData<Product>) {
        response.observe(this, Observer {
            if (it.data.size > 0) {
                productsData.addAll(it.data)
                var i = 0
                while (i < 5) {
                    products.add(productsData.get(i))
                    i++
                }
                productAdapter!!.notifyDataSetChanged()
                isLoading = false
            }
        })

    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

interface ProductCallBack {
    fun onStarted()
    fun onSuccess(products: MutableLiveData<Product>)
    fun onError(message: String)
}