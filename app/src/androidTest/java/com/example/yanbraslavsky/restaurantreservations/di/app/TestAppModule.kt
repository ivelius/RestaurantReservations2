package com.example.yanbraslavsky.restaurantreservations.di.app

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.di.AppModule
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class TestAppModule(mApp: App) : AppModule(mApp) {

    //we leave a possibility to change that presenter before the injection
    var mMockedMainPresenter: MainContract.Presenter? = null


    @Provides
    override fun provideMainPresenter(): MainContract.Presenter {
        return mMockedMainPresenter ?: MainPresenter()
    }

}