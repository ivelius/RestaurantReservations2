package com.example.yanbraslavsky.restaurantreservations

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class TableSelectionActivity : AppCompatActivity() {

    companion object {
        fun open(fromContext: Context) {
            fromContext.startActivity(Intent(fromContext, TableSelectionActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_selection)
    }
}
