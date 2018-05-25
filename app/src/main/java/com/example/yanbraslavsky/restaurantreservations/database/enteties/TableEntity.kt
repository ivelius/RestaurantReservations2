package com.example.yanbraslavsky.restaurantreservations.database.enteties

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TableEntity(
        @PrimaryKey(autoGenerate = true)
        val uid: Long,
        val available: Boolean,

        //It is easier to track tables by their serial numbers
        //in this case the serial number of the table corresponds to its
        //index in the json array
        val tableNumber: Int ,

        //We will store a reference to the customer that has reserved the table
        //later on this customer will have the possibility to unselect his table and thus
        //make it available for the others
        var reservedByCustomerId : Int? = null
) : Parcelable