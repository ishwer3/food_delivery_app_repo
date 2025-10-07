package com.example.deliveryapp.di.module

import com.example.deliveryapp.data.repository.AuthRepositoryImpl
import com.example.deliveryapp.data.repository.MealRepositoryImpl
import com.example.deliveryapp.domain.repository.AuthRepository
import com.example.deliveryapp.domain.repository.MealRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindMealRepository(
        mealRepositoryImpl: MealRepositoryImpl
    ): MealRepository
}