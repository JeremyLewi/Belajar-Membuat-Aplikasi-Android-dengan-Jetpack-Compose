package com.example.jetgarage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetgarage.data.CarRepository
import com.example.jetgarage.ui.screen.cart.CartViewModel
import com.example.jetgarage.ui.screen.detail.DetailCarViewModel
import com.example.jetgarage.ui.screen.home.HomeViewModel


class ViewModelFactory(private val repository: CarRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailCarViewModel::class.java)) {
            return DetailCarViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}