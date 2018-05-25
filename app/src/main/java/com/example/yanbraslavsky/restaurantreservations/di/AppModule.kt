package com.example.yanbraslavsky.restaurantreservations.di

import com.example.yanbraslavsky.restaurantreservations.api.RestarauntService
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import com.example.yanbraslavsky.restaurantreservations.usecases.CustomersUseCase
import com.example.yanbraslavsky.restaurantreservations.usecases.impl.CustomersUseCaseImpl
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


    //Presenters
    @Provides
    open fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    open fun provideCustomersPresenter(customersUseCase: CustomersUseCase): CustomersContract.Presenter {
        return CustomersPresenter(customersUseCase)
    }

}