package com.example.yanbraslavsky.restaurantreservations

import android.app.Application
import com.example.yanbraslavsky.restaurantreservations.app.AppComponent
import com.example.yanbraslavsky.restaurantreservations.app.AppModule
import com.example.yanbraslavsky.restaurantreservations.app.DaggerAppComponent

/**
 * Created by yan.braslavski on 11/13/17.
 */
class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule())
                .build()
    }
}
