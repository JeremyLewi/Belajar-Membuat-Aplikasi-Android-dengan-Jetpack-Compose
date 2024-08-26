package com.example.jetgarage.di

import com.example.jetgarage.data.CarRepository


object Injection {
    fun provideRepository(): CarRepository {
        return CarRepository.getInstance()
    }
}