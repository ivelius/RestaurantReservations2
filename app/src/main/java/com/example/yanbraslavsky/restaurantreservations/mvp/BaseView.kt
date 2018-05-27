package com.example.yanbraslavsky.restaurantreservations.mvp

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.yanbraslavsky.restaurantreservations.R
import kotlinx.android.synthetic.main.activity_customer_selection.*


abstract class BaseView : AppCompatActivity(), BaseContract.View {

    override fun showMessage(message: String) {
        Snackbar.make(findAnchorView(), message, Snackbar.LENGTH_LONG).show()
    }


    override fun showLoading() {
        loading_overlay?.let {
            it.visibility = View.VISIBLE
            it.animate().alpha(1f)
        }
    }

    override fun stopLoading() {
        loading_overlay?.let {
            it.visibility = View.VISIBLE
            it.animate().alpha(0f).withEndAction({
                it.visibility = View.GONE
            })
        }
    }

    override fun changeTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun showError(errorMessage: String) {
        val snack = Snackbar.make(findAnchorView(), errorMessage, Snackbar.LENGTH_LONG)
        val tv = snack.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        tv.setTextColor(Color.RED)
        snack.show()
    }

    private fun findAnchorView() = findViewById(R.id.constraintLayout) ?: window.decorView.rootView
}