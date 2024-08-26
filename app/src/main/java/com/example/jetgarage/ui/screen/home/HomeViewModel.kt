package com.example.jetgarage.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetgarage.data.CarRepository
import com.example.jetgarage.model.OrderCar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.example.jetgarage.ui.common.UiState


class HomeViewModel(
    private val repository: CarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderCar>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderCar>>>
        get() = _uiState

    fun getAllCars() {
        viewModelScope.launch {
            repository.getAllCars()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderCars ->
                    _uiState.value = UiState.Success(orderCars)
                }
        }
    }


    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchCars(newQuery)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderCars ->
                    _uiState.value = UiState.Success(orderCars)
                }
        }
    }


}