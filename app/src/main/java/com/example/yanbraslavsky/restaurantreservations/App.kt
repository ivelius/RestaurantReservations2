package com.example.yanbraslavsky.restaurantreservations

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.yanbraslavsky.restaurantreservations.di.AppComponent
import com.example.yanbraslavsky.restaurantreservations.di.AppModule
import com.example.yanbraslavsky.restaurantreservations.di.DaggerAppComponent
import com.example.yanbraslavsky.restaurantreservations.workscheduling.TableFreeWorker
import java.time.Duration

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
                .appModule(AppModule(this))
                .build()
    }
}
