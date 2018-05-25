package com.example.yanbraslavsky.restaurantreservations.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import io.reactivex.Single

@Dao
interface CustomerDao {

    @Query("SELECT * FROM CustomerEntity")
    fun getAllPeople(): Single<List<CustomerEntity>>

    @Insert
    fun insert(customer: CustomerEntity)
}