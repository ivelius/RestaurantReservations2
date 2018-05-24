package com.example.yanbraslavsky.restaurantreservations

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class CustomerSelectionActivity : AppCompatActivity() {

    companion object {
        fun open(fromContext: Context) {
            fromContext.startActivity(Intent(fromContext, CustomerSelectionActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_selection)
    }
}
