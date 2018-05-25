package com.example.yanbraslavsky.restaurantreservations.di

import android.arch.persistence.room.Room
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersUseCaseImpl
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationUseCaseImpl
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersUseCase
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
open class AppModule(private val mApp: App) {

    @Provides
    @Singleton
    open fun provideApi() = RestaurantService.create()

    @Provides
    @Singleton
    open fun provideDatabase(): RestaurantDatabase {
        return Room.databaseBuilder(mApp, RestaurantDatabase::class.java, "restaraunt-db").build()
    }

    //Use Cases
    @Provides
    open fun provideCustomersUseCase(apiService: RestaurantService, database: RestaurantDatabase): CustomersUseCase {
        return CustomersUseCaseImpl(apiService, database)
    }

    @Provides
    open fun provideReservationUseCase(apiService: RestaurantService, database: RestaurantDatabase): ReservationUseCase {
        return ReservationUseCaseImpl(apiService, database)
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

