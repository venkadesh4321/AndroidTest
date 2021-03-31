package com.venkad.androidtest.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.venkad.androidtest.R
import com.venkad.androidtest.databinding.ActivityLoginBinding
import com.venkad.androidtest.ui.products.ProductsActivity
import com.venkad.androidtest.ui.register.RegisterActivity
import com.venkad.androidtest.util.Constants
import khangtran.preferenceshelper.PrefHelper
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware, LoginCallBack {
    // injection
    override val kodein by kodein()

    private val TAG = "LoginActivity"
    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private val loginViewModelFactory: LoginViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Preference Helper
        PrefHelper.initHelper(this, "AndroidTest")

        val token = PrefHelper.getStringVal(Constants.KEY_TOKEN)
        if (!token.isNullOrEmpty()) {
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindViews()
        initViewModel()
        initListeners()

    }

    private fun bindViews() {
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    private fun initViewModel() {
        loginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
        loginViewModel.loginCallBack = this
    }

    private fun initListeners() {
        activityLoginBinding.registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        activityLoginBinding.loginBtn.setOnClickListener{
            when {
                activityLoginBinding.emailInputLayout.editText?.text.isNullOrEmpty() -> {
                    Log.e(TAG, "initListeners: ")
                    Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
                }
                !activityLoginBinding.emailInputLayout.editText?.text.toString().isValidEmail() -> {
                    Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
                }
                activityLoginBinding.passwordInputLayout.editText?.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    loginViewModel.login(activityLoginBinding.emailInputLayout.editText?.text.toString(), activityLoginBinding.passwordInputLayout.editText?.text.toString())
                }
            }
        }
    }

    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    override fun onStarted() {
        Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

interface LoginCallBack {
    fun onStarted()
    fun onSuccess(message: String)
    fun onError(message: String)
}