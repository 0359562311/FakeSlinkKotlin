package com.example.fakeslink.app.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fakeslink.R
import com.example.fakeslink.app.viewmodel.*
import com.example.fakeslink.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var signInViewModelFactory: SignInViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        signInViewModelFactory = SignInViewModelFactory()
        binding.viewModel = ViewModelProvider(this, signInViewModelFactory)
            .get(LoginViewModel::class.java)
        binding.lifecycleOwner = this
        observeEditText()
        observeState()
        binding.loginLoginButton.setOnClickListener {
            if ((binding.loginUsername.text ?: "").isEmpty()) {
                binding.loginUsernameLayout.error = getString(R.string.at_least_6_characters)
            }
            if ((binding.loginPassword.text ?: "").isEmpty()) {
                binding.loginPasswordLayout.error = getString(R.string.at_least_6_characters)
            }
            if (binding.loginUsernameLayout.error == null && binding.loginPasswordLayout.error == null) {
                binding.viewModel?.login()
            }
        }
    }

    private fun observeEditText() {
        binding.loginUsername.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if((s?:"").length < 6) {
                    binding.loginUsernameLayout.error = getString(R.string.at_least_6_characters)
                } else {
                    binding.loginUsernameLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.loginPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if((s?:"").length < 6) {
                    binding.loginPasswordLayout.error = getString(R.string.at_least_6_characters)
                } else {
                    binding.loginPasswordLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun observeState() {
        binding.viewModel?.loginState?.observe(this
        ) { loginState ->
            when (loginState) {
                is LoginFailState -> {
                    MyAlertDialog.showAlertDialog(this, "Error", loginState.message)
                }
                is LoginLoadingState -> {
                    hideKeyboard()
                }
                is LoginSuccessfulState -> {
                    setResult(RESULT_OK)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(this.currentFocus?.windowToken,0)
    }
}