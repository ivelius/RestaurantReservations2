package com.example.yanbraslavsky.restaurantreservations.repositories.reservations

import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReservationsRepositoryImpl
@Inject constructor(private val mApiService: RestaurantService,
                    private val mRestaurantDatabase: RestaurantDatabase) : ReservationsRepository {


    override fun getTables(): Single<List<TableEntity>> {
        //Try loading the data from local data base
        return databaseObservable()
                //if data from local data base cannot be loaded , try loading from api
                .onErrorResumeNext(apiObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun databaseObservable(): Single<List<TableEntity>> {
        return Single.create {
            val list = mRestaurantDatabase.tableDao().getTables().blockingGet()
            if (list.isEmpty()) {
                it.onError(Error("Empty List"))
            } else {
                it.onSuccess(list)
            }
        }
    }

    private fun apiObservable(): Single<List<TableEntity>> {
        return Single.create {
            val list = mApiService.getTables().blockingGet()
            if (list == null || list.isEmpty()) {
                it.onError(Error("Error"))
            } else {
                var index = 0
                list.forEach({
                    val table = TableEntity(0, it, index++)
                    mRestaurantDatabase.tableDao().insert(table)
                })

                it.onSuccess(mRestaurantDatabase.tableDao().getTables().blockingGet())
            }
        }
    }


    override fun updateTable(table: TableEntity): Single<TableEntity> {
        return Single.just(table)
                .doOnSuccess({
                    mRestaurantDatabase.tableDao().update(it)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}