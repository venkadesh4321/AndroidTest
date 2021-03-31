package com.venkad.androidtest.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.venkad.androidtest.data.repository.CustomerRepository

class LoginViewModelFactory(
    private val customerRepository: CustomerRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(customerRepository) as T
    }
}