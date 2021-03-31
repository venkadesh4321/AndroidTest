package com.venkad.androidtest

import android.app.Application
import com.venkad.androidtest.data.network.NetworkConnectionInterceptor
import com.venkad.androidtest.data.network.api.CustormerApi
import com.venkad.androidtest.data.network.api.ProductApi
import com.venkad.androidtest.data.repository.CustomerRepository
import com.venkad.androidtest.data.repository.ProductRepository
import com.venkad.androidtest.ui.login.LoginViewModelFactory
import com.venkad.androidtest.ui.products.ProductsViewModelFactory
import com.venkad.androidtest.ui.register.RegisterViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { CustormerApi(instance()) }
        bind() from singleton { ProductApi(instance()) }
        bind() from singleton { CustomerRepository(instance()) }
        bind() from singleton { ProductRepository(instance()) }

        bind() from singleton { LoginViewModelFactory(instance()) }
        bind() from singleton { RegisterViewModelFactory(instance()) }
        bind() from singleton { ProductsViewModelFactory(instance()) }
    }
}