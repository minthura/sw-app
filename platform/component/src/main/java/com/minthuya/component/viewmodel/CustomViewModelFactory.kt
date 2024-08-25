package com.minthuya.component.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class CustomViewModelFactory(private val viewModel: BaseViewModel) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}

abstract class BaseViewModel : ViewModel()

inline fun <reified T : BaseViewModel> BaseViewModel.getViewModel(owner: ViewModelStoreOwner): T {
    return ViewModelProvider(owner = owner, CustomViewModelFactory(this))[T::class.java]
}