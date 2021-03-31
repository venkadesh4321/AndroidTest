package com.venkad.androidtest.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.venkad.androidtest.data.repository.CustomerRepository

class RegisterViewModelFactory(
    private val customerRepository: CustomerRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(customerRepository) as T
    }
}