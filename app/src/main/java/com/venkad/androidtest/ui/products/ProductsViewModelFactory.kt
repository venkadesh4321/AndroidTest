package com.venkad.androidtest.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.venkad.androidtest.data.repository.ProductRepository

class ProductsViewModelFactory(
    private val productRepository: ProductRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductsViewModel(productRepository) as T
    }
}