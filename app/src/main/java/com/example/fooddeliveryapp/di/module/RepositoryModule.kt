package com.example.fooddeliveryapp.di.module

import com.example.fooddeliveryapp.data.repository.AuthRepositoryImpl
import com.example.fooddeliveryapp.data.repository.MealRepositoryImpl
import com.example.fooddeliveryapp.domain.repository.AuthRepository
import com.example.fooddeliveryapp.domain.repository.MealRepository
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