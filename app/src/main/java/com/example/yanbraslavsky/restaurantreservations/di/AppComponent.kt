package com.example.yanbraslavsky.restaurantreservations.di

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.repositories.customers.CustomersRepositoryImpl
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersView
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainView
import com.example.yanbraslavsky.restaurantreservations.repositories.reservations.ReservationsRepositoryImpl
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationView
import com.example.yanbraslavsky.restaurantreservations.workscheduling.TableFreeWorker
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {


    //Views
    fun inject(mainView: MainView)

    fun inject(customersView: CustomersView)
    fun inject(reservationView: ReservationView)

    //Use cases
    fun inject(customerUseCase: CustomersRepositoryImpl)

    fun inject(reservationUseCaseImpl: ReservationsRepositoryImpl)

    //Workers
    fun inject(tableFreeWorker: TableFreeWorker)

    fun inject(app: App)
}