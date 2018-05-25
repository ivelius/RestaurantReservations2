package com.example.yanbraslavsky.restaurantreservations.database.enteties

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class CustomerEntity(
        @PrimaryKey(autoGenerate = true)
        val uid: Long,
        val customerFirstName: String,
        val customerLastName: String,
        val customerId: Int
)