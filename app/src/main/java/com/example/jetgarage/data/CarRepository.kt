package com.example.jetgarage.data

import com.example.jetgarage.model.FakeCarDataSource
import com.example.jetgarage.model.OrderCar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class CarRepository {

    private val orderCars = mutableListOf<OrderCar>()

    init {
        if (orderCars.isEmpty()) {
            FakeCarDataSource.dummyCars.forEach {
                orderCars.add(OrderCar(it, 0))
            }
        }
    }

    fun getAllCars(): Flow<List<OrderCar>> {
        return flowOf(orderCars)
    }

    fun getOrderCarById(carId: Long): OrderCar {
        return orderCars.first {
            it.car.id == carId
        }
    }

    fun updateOrderCar(carId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderCars.indexOfFirst { it.car.id == carId }
        val result = if (index >= 0) {
            val orderCar = orderCars[index]
            orderCars[index] =
                orderCar.copy(car = orderCar.car, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderCars(): Flow<List<OrderCar>> {
        return getAllCars()
            .map { orderCars ->
                orderCars.filter { orderCar ->
                    orderCar.count != 0
                }
            }
    }

    fun searchCars(query: String): Flow<List<OrderCar>> {
        return getAllCars()
            .map { orderCars ->
                orderCars.filter { orderCar ->
                    orderCar.car.title.contains(query, ignoreCase = true)
                }
            }
    }

    companion object {
        @Volatile
        private var instance: CarRepository? = null

        fun getInstance(): CarRepository =
            instance ?: synchronized(this) {
                CarRepository().apply {
                    instance = this
                }
            }
    }
}