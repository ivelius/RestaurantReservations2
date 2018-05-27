package com.example.yanbraslavsky.restaurantreservations.utils

import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import java.util.*

class StubsProvider {

    companion object {

        val INDEX_OF_AVAILABLE_NOT_SELECTED_TABLE: Int = 0
        val INDEX_OF_TABLE_RESERVED_BY_OTHER_CUSTOMER: Int = 1
        val INDEX_OF_UNAVAILABLE_TABLE: Int = 2
        val INDEX_OF_TABLE_RESERVED_BY_GIVEN_CUSTOMER: Int = 3

        fun createFakeTablesList(fakeCustomerId: Int): List<ReservationContract.GridCellTableModel> {
            val fakeData = ArrayList<ReservationContract.GridCellTableModel>()

            //first table is available and not selected
            fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, true, 0), false, false))

            //second table is reserved by someone else
            fakeData.add(ReservationContract.GridCellTableModel(
                    TableEntity(0, true, 0, 98), false, true))

            //third table is unavailable
            fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, false, 0), false, false))


            //fourth table is reserved by current fake customer and hence is selected
            fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, true, 0, fakeCustomerId), true, false))

            //rest of the tables are random
            val random = Random()
            for (i in fakeData.size..10) {
                fakeData.add(ReservationContract.GridCellTableModel(TableEntity(0, random.nextBoolean(), 0), false, false))
            }
            return fakeData
        }
    }
}