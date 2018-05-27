package com.example.yanbraslavsky.restaurantreservations.presenters

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationPresenter
import com.example.yanbraslavsky.restaurantreservations.repositories.reservations.ReservationsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class ReservationsPresenterTest {

    private lateinit var mReservationPresenter: ReservationPresenter
    private lateinit var mReservationView: ReservationContract.View
    private lateinit var mReservationsRepository: ReservationsRepository
    private val mFakeCustomer = CustomerEntity(0, "Uncle", "Bob", 1)
    private val mFakeTables = createFakeTablesList()

    @Before
    fun setUp() {
        mReservationsRepository = mock()
        Mockito.`when`(mReservationsRepository.getRemoteTablesList())
                .thenReturn(Single.just(mFakeTables))

        Mockito.`when`(mReservationsRepository.updateTable(any()))
                .thenReturn(Single.just(mFakeTables.first()))
        mReservationView = mock()
        mReservationPresenter = ReservationPresenter(mReservationsRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun bindView_Test() {
        //first we want to set the customer we are working with
        mReservationPresenter.setCustomer(mFakeCustomer)
        //make sure after we bind the view  , title will change and contain the customer name
        mReservationPresenter.bind(mReservationView)
        val customerNameMatcherRegex = "(.*${mFakeCustomer.customerFirstName}.*${mFakeCustomer.customerLastName}.*)|(.*${mFakeCustomer.customerLastName}.*${mFakeCustomer.customerFirstName}.*)"
        verify(mReservationView).changeTitle(Mockito.matches(customerNameMatcherRegex))

        //make sure presenter will call show tables on the view
        //at this point we know that data will be different , since
        //it is being wrapped by another viewmodel
        verify(mReservationView).showTables(any())
    }

    @Test
    fun onItemClicked_Test() {
        mReservationPresenter.setCustomer(mFakeCustomer)
        mReservationPresenter.bind(mReservationView)

        //we know first table is not reserved
        val notReservedTable = mFakeTables.first()

        //we simulate the table data click
        val simulateClickedItem = ReservationContract.GridCellTableModel(notReservedTable, false, false)
        mReservationPresenter.onTableItemClick(simulateClickedItem)

        //TODO : Need to assert that the table is selected
//        assert(simulateClickedItem.mSelected)

        //make sure we update the use case with the data change
        verify(mReservationsRepository).updateTable(notReservedTable)

        //make sure presenter updates the view with data change
        verify(mReservationView).updateTable(any())
    }


    private fun createFakeTablesList(): List<TableEntity> {
        val fakeData = ArrayList<TableEntity>()

        //first table is available
        fakeData.add(TableEntity(0, true, 0))
        //second table is reserved by someone else
        fakeData.add(TableEntity(0, false, 1, 89))

        //rest of the tables are random
        val random = Random()
        for (i in 2..10) {
            fakeData.add(TableEntity(0, random.nextBoolean(), i, 5))
        }
        return fakeData
    }
}