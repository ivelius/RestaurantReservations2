package com.example.yanbraslavsky.restaurantreservations.di

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersView
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainView
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationView
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersUseCaseImpl
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationUseCaseImpl
import dagger.Component
import javax.inject.Singleton
import dagger.BindsInstance



@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {


    //Views
    fun inject(mainView: MainView)
    fun inject(customersView: CustomersView)
    fun inject(reservationView: ReservationView)

    //Use cases
    fun inject(customerUseCase: CustomersUseCaseImpl)
    fun inject(reservationUseCaseImpl: ReservationUseCaseImpl)
}