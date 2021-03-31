package com.venkad.androidtest.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.venkad.androidtest.R
import com.venkad.androidtest.databinding.ActivityRegisterBinding
import com.venkad.androidtest.ui.login.LoginViewModelFactory
import com.venkad.androidtest.ui.login.LoginActivity
import com.venkad.androidtest.ui.products.ProductsActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(), KodeinAware, RegisterCallBack {
    // injection
    override val kodein by kodein()

    lateinit var activityRegisterBinding: ActivityRegisterBinding
    lateinit var registerViewModel: RegisterViewModel
    private val registerViewModelFactory: RegisterViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
        initViewModel()
        initListener()
    }

    private fun bindViews() {
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
    }

    private fun initViewModel() {
        registerViewModel = ViewModelProvider(this, registerViewModelFactory).get(RegisterViewModel::class.java)
        registerViewModel.registerCallBack = this
    }

    private fun initListener() {
        activityRegisterBinding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        activityRegisterBinding.registerBtn.setOnClickListener{
            when {
                activityRegisterBinding.emailInputLayout.editText?.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Enter email id", Toast.LENGTH_SHORT).show()
                }
                !activityRegisterBinding.emailInputLayout.editText?.text.toString().isValidEmail() -> {
                    Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
                }
                activityRegisterBinding.passwordInputLayout.editText?.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
                }
                activityRegisterBinding.confirmPasswordInputLayout.editText?.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Enter confirm password", Toast.LENGTH_SHORT).show()
                }
                activityRegisterBinding.passwordInputLayout.editText?.text.toString() != activityRegisterBinding.confirmPasswordInputLayout.editText?.text.toString() -> {
                    Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    registerViewModel.register(activityRegisterBinding.emailInputLayout.editText?.text.toString(),  activityRegisterBinding.passwordInputLayout.editText?.text.toString())
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

interface RegisterCallBack {
    fun onStarted()
    fun onSuccess(message: String)
    fun onError(message: String)
}