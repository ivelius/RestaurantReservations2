package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.database.RestarauntDatabase
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReservationUseCaseImpl
@Inject constructor(private val mApiService: RestaurantService,
                    private val mRestaurantDatabase: RestarauntDatabase) : ReservationUseCase {

    init {
        App.appComponent.inject(this)
    }

    override fun getRemoteTablesList(): Single<List<TableEntity>> {
        //Try loading the data from local data base
        return mRestaurantDatabase.tableDao().getTables()
                .flatMap {
                    if (!it.isEmpty())
                        return@flatMap Single.just(it) else {
                        //no data in database , load from server
                        return@flatMap mApiService.getTables()
                                .map {

                                    var index = 0
                                    //when the data is loaded from server we store it
                                    //in the data base for future use
                                    it.map {
                                        val table = TableEntity(0, it, index++)
                                        mRestaurantDatabase.tableDao().insert(table)
                                        return@map table
                                    }
                                }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}