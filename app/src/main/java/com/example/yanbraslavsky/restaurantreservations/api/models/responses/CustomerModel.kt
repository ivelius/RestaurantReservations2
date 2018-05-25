package com.example.yanbraslavsky.restaurantreservations.api.models.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CustomerModel(
        val customerFirstName: String,
        val customerLastName: String,
        val id: Int
) : Parcelable