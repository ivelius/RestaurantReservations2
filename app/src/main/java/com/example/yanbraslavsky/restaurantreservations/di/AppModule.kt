package com.example.yanbraslavsky.restaurantreservations.di

import android.arch.persistence.room.Room
import android.content.Context
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.persistance.AppPreferences
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersPresenter
import com.example.yanbraslavsky.restaurantreservations.repositories.customers.CustomersRepository
import com.example.yanbraslavsky.restaurantreservations.repositories.customers.CustomersRepositoryImpl
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationPresenter
import com.example.yanbraslavsky.restaurantreservations.repositories.reservations.ReservationsRepository
import com.example.yanbraslavsky.restaurantreservations.repositories.reservations.ReservationsRepositoryImpl
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
    open fun providePreferences() = AppPreferences(mApp.getSharedPreferences(mApp.packageName, Context.MODE_PRIVATE))

    @Provides
    @Singleton
    open fun provideDatabase(): RestaurantDatabase {
        return Room.databaseBuilder(mApp, RestaurantDatabase::class.java, "restaraunt-db").build()
    }

    //Use Cases
    @Provides
    open fun provideCustomersUseCase(apiService: RestaurantService, database: RestaurantDatabase): CustomersRepository {
        return CustomersRepositoryImpl(apiService, database)
    }

    @Provides
    open fun provideReservationUseCase(apiService: RestaurantService, database: RestaurantDatabase): ReservationsRepository {
        return ReservationsRepositoryImpl(apiService, database)
    }


    //Presenters
    @Provides
    open fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    open fun provideReservationPresenter(reservationsRepository: ReservationsRepository): ReservationContract.Presenter {
        return ReservationPresenter(reservationsRepository)
    }

    @Provides
    open fun provideCustomersPresenter(customersRepository: CustomersRepository): CustomersContract.Presenter {
        return CustomersPresenter(customersRepository)
    }

}

