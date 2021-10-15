package com.example.fakeslink.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//We can not create ViewModel on our own. We need ViewModelProviders utility provided by Android to create ViewModels.
//
//But ViewModelProviders can only instantiate ViewModels with no arg constructor.
//
//So if I have a ViewModel with multiple arguments, then I need to use a Factory
//that I can pass to ViewModelProviders to use when an instance of MyViewModel is required
class SignInViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}