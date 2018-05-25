package com.example.yanbraslavsky.restaurantreservations.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.yanbraslavsky.restaurantreservations.database.daos.CustomerDao
import com.example.yanbraslavsky.restaurantreservations.database.daos.TableDao
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity

@Database(entities = arrayOf(CustomerEntity::class, TableEntity::class), version = 1)
abstract class RestarauntDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun tableDao(): TableDao
}