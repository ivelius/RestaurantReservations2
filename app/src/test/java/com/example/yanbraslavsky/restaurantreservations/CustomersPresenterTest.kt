package com.example.yanbraslavsky.restaurantreservations

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersUseCase
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CustomersPresenterTest {

    private lateinit var mCustomersPresenter: CustomersPresenter
    private lateinit var mCustomersView: CustomersContract.View
    private lateinit var mCustomersUseCase: CustomersUseCase
    private val mFakeCustomers: List<CustomerEntity> by lazy {
        createListOfFakeCustomers()
    }

    @Before
    fun setUp() {
        mCustomersUseCase = mock()
        Mockito.`when`(mCustomersUseCase.getCustomers())
                .thenReturn(Single.just(mFakeCustomers))

        mCustomersView = mock()
        mCustomersPresenter = CustomersPresenter(mCustomersUseCase)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun bindView_Test() {
        //make sure after we bind the view  , presenter will use the provided
        //usecase to show the customers on the view
        mCustomersPresenter.bind(mCustomersView)
        verify(mCustomersView).showCustomers(mFakeCustomers)
    }

    @Test
    fun onItemClicked_Test() {
        //when one of the view items is clicked , make sure
        //presenter will tell the view to open a reservations screen
        //for the selected customer
        mCustomersPresenter.bind(mCustomersView)
        val firstCustomer = mFakeCustomers.first()
        mCustomersPresenter.onItemClicked(firstCustomer)
        verify(mCustomersView).openReservationScreenForCustomer(firstCustomer)
    }


    private fun createListOfFakeCustomers(): List<CustomerEntity> {
        val fakeData = ArrayList<CustomerEntity>()
        for (i in 0..10) {
            fakeData.add(CustomerEntity(0, "fake", "$i", i))
        }
        return fakeData
    }
}