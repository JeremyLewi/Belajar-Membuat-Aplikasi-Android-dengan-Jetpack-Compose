package com.example.jetgarage.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetgarage.data.CarRepository
import com.example.jetgarage.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderCars() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderCars()
                .collect { orderCar ->
                    val totalRequiredPrice =
                        orderCar.sumOf { it.car.requiredPrice * it.count }
                    _uiState.value = UiState.Success(CartState(orderCar, totalRequiredPrice))
                }
        }
    }

    fun updateOrderCar(carId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderCar(carId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderCars()
                    }
                }
        }
    }
}