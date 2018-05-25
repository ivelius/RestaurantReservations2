package com.example.yanbraslavsky.restaurantreservations.app

import com.example.yanbraslavsky.restaurantreservations.api.RestarauntService
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {

    @Provides
    @Singleton
    open fun provideApi() = RestarauntService.create()

    @Provides
    open fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }


}