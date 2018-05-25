package com.example.yanbraslavsky.restaurantreservations.di

import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersView
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainView
import com.example.yanbraslavsky.restaurantreservations.usecases.impl.CustomersUseCaseImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    //Views
    fun inject(mainView: MainView)

    fun inject(customersView: CustomersView)

    //Use cases
    fun inject(customerUseCase: CustomersUseCaseImpl)
}