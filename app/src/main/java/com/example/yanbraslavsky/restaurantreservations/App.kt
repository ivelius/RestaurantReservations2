package com.example.yanbraslavsky.restaurantreservations

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.yanbraslavsky.restaurantreservations.di.AppComponent
import com.example.yanbraslavsky.restaurantreservations.di.AppModule
import com.example.yanbraslavsky.restaurantreservations.di.DaggerAppComponent
import com.example.yanbraslavsky.restaurantreservations.persistance.AppPreferences
import com.example.yanbraslavsky.restaurantreservations.workscheduling.TableFreeWorker
import java.time.Duration
import javax.inject.Inject

/**
 * Created by yan.braslavski on 11/13/17.
 */
class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    @Inject
    lateinit var mPreferences: AppPreferences

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()

        appComponent.inject(this)

        if (mPreferences.isFirstLaunch()) {
            scheduleCleanup()
        }
    }


    private fun scheduleCleanup() {
        // using the fancy brand new WorkManager ()
        // https://developer.android.com/topic/libraries/architecture/workmanager
        // I am still not sure whether this thing is reliable , but google claims it is ...
        WorkManager.getInstance()
                .enqueue(PeriodicWorkRequest.Builder(TableFreeWorker::class.java,
                        Duration.ofMinutes(BuildConfig.TABLE_REFRESH_INTERVAL_MINUTES))
                        .build())
    }
}
