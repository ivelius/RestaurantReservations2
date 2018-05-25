package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.mvp.BaseContract


object ReservationContract {

    //We declare this view model for selection tracking
    class GridCellTableModel(val mTableEntity: TableEntity, var mSelected: Boolean)

    /**
     * Here we define the communication :
     * Presenter -> View
     */
    interface View : BaseContract.View {
        fun showTables(data: List<GridCellTableModel>)
        fun updateTable(tableItem: GridCellTableModel)
    }

    /**
     * Here we define the communication :
     * View -> Presenter
     */
    interface Presenter : BaseContract.Presenter<View> {
        fun onTableItemClick(tableItem: GridCellTableModel)
        fun setCustomer(customer: CustomerEntity)
    }
}