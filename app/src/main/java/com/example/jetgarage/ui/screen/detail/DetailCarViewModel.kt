package com.example.jetgarage.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetgarage.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.jetgarage.data.CarRepository
import com.example.jetgarage.model.Car
import com.example.jetgarage.model.OrderCar

class DetailCarViewModel(
    private val repository: CarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderCar>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderCar>>
        get() = _uiState

    fun getCarById(carId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderCarById(carId))
        }
    }

    fun addToCart(car: Car, count: Int) {
        viewModelScope.launch {
            repository.updateOrderCar(car.id, count)
        }
    }
}