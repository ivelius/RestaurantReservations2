package com.example.yanbraslavsky.restaurantreservations.repositories.reservations

import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import io.reactivex.Single


interface ReservationsRepository {

    fun getTables(): Single<List<TableEntity>>
    fun updateTable(tables: TableEntity) : Single<TableEntity>
}