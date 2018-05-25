package com.example.yanbraslavsky.restaurantreservations.screens.customers

import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseContract


object CustomersContract {

    /**
     * Here we define the communication :
     * Presenter -> View
     */
    interface View : BaseContract.View {
        fun showCustomers(data: List<CustomerModel>)
    }

    /**
     * Here we define the communication :
     * View -> Presenter
     */
    interface Presenter : BaseContract.Presenter<View> {
    }
}