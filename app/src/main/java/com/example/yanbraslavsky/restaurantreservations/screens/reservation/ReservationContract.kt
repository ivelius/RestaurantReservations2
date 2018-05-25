package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseContract


object ReservationContract {

    /**
     * Here we define the communication :
     * Presenter -> View
     */
    interface View : BaseContract.View {
        fun showTables(data: List<TableEntity>)
    }

    /**
     * Here we define the communication :
     * View -> Presenter
     */
    interface Presenter : BaseContract.Presenter<View> {
        fun onTableItemClick(tableItem: TableEntity)
    }
}