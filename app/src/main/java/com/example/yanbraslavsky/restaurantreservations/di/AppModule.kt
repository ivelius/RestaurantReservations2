package com.example.yanbraslavsky.restaurantreservations.di

import com.example.yanbraslavsky.restaurantreservations.api.RestarauntService
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationPresenter
import com.example.yanbraslavsky.restaurantreservations.usecases.CustomersUseCase
import com.example.yanbraslavsky.restaurantreservations.usecases.ReservationUseCase
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersUseCaseImpl
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {

    @Provides
    @Singleton
    open fun provideApi() = RestarauntService.create()

    //Use Cases
    @Provides
    open fun provideCustomersUseCase(apiService: RestarauntService): CustomersUseCase {
        return CustomersUseCaseImpl(apiService)
    }

    @Provides
    open fun provideReservationUseCase(apiService: RestarauntService): ReservationUseCase {
        return ReservationUseCaseImpl(apiService)
    }


    //Presenters
    @Provides
    open fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    open fun provideReservationPresenter(reservationUseCase: ReservationUseCase): ReservationContract.Presenter {
        return ReservationPresenter(reservationUseCase)
    }

    @Provides
    open fun provideCustomersPresenter(customersUseCase: CustomersUseCase): CustomersContract.Presenter {
        return CustomersPresenter(customersUseCase)
    }

}