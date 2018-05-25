package com.example.yanbraslavsky.restaurantreservations.workscheduling

import android.support.annotation.WorkerThread
import android.util.Log
import androidx.work.Worker
import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import javax.inject.Inject


/**
 * This worker will make tables that we have in database available
 * for every customer to use
 */
@WorkerThread
class TableFreeWorker :
        Worker() {

    @Inject
    lateinit var mRestaurantDatabase: RestaurantDatabase

    init {
        App.appComponent.inject(this)
    }

    override fun doWork(): Worker.WorkerResult {
        Log.d(TableFreeWorker::class.java.simpleName, "Cleaning Tables...")
        makeTablesAvailable()
        return WorkerResult.SUCCESS
    }

    private fun makeTablesAvailable() {
        val tables = mRestaurantDatabase.tableDao().getTables().blockingGet()
        //we delete all tables in data base
        mRestaurantDatabase.tableDao().deleteTables()

        //fill the database with the same tables but not reserved and free
        tables.forEach({
            mRestaurantDatabase.tableDao()
                    .insert(TableEntity(0, true, it.tableNumber))
        })
    }
}