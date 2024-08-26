package com.example.jetgarage.ui.screen.cart

import com.example.jetgarage.model.OrderCar

data class CartState(
    val orderCar: List<OrderCar>,
    val totalRequiredPrice: Int
)