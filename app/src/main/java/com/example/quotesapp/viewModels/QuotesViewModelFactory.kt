package com.example.quotesapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuotesViewModelFactory(private val context:Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuotesViewModel(context) as T
    }
}