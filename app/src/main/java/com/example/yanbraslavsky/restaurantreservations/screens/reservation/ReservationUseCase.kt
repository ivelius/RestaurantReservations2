package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import io.reactivex.Single


interface ReservationUseCase {

    fun getRemoteTablesList(): Single<List<TableEntity>>
}