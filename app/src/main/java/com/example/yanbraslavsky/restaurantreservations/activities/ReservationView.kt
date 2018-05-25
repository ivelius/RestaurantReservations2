package com.example.yanbraslavsky.restaurantreservations.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel

class ReservationView : AppCompatActivity() {

    companion object {
        fun open(fromContext: Context, withCustomerModel: CustomerModel) {
            fromContext.startActivity(Intent(fromContext, ReservationView::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_selection)
    }
}
