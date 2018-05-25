package com.example.yanbraslavsky.restaurantreservations.database.enteties

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class TableEntity(
        @PrimaryKey(autoGenerate = true)
        val uid: Long,
        val reserved: Boolean,

        //It is easier to track tables by their serial numbers
        //in this case the serial number of the table corresponds to its
        //index in the json array
        val tableNumber: Int
)