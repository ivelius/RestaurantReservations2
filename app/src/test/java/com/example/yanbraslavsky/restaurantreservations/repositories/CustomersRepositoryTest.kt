package com.example.yanbraslavsky.restaurantreservations.repositories

import com.affinitas.task.utils.RxUtils
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.database.daos.CustomerDao
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.repositories.customers.CustomersRepository
import com.example.yanbraslavsky.restaurantreservations.repositories.customers.CustomersRepositoryImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CustomersRepositoryTest {

    private lateinit var mCustomersRepository: CustomersRepository
    private lateinit var mApiService: RestaurantService
    private lateinit var mRestaurantDatabase: RestaurantDatabase
    private lateinit var mCustomersDao: CustomerDao

    @Before
    fun setUp() {

        //We are not really interested in testing multithreaded loading at this point
        //So we just constrain all Rx operations for a single thread
        RxUtils.makeRxSchedulersImmediate()

        mCustomersDao = mock()
        mApiService = mock()
        mRestaurantDatabase = mock()
        mCustomersRepository =
                CustomersRepositoryImpl(mApiService, mRestaurantDatabase)

        Mockito.`when`(mRestaurantDatabase.customerDao())
                .thenReturn(mCustomersDao)
    }

    @After
    fun tearDown() {
        RxUtils.resetRxSchedulers()
    }

    @Test
    fun emptyDatabase_Test() {

        //we want our database to return an empty list
        //when repository tries to call a data from it
        val dataBaseList: ArrayList<CustomerEntity> = ArrayList()
        Mockito.`when`(mCustomersDao.getCustomers())
                .thenReturn(Single.just(dataBaseList))


        //lets make the api return an empty list as well
        val apiList: List<CustomerModel> = ArrayList()
        Mockito.`when`(mApiService.getCustomers())
                .thenReturn(Single.just(apiList))

        //call the method under test
        mCustomersRepository.getCustomers().subscribe({}, {})


        //make sure database and DAO methods are called
        verify(mCustomersDao).getCustomers()


        //since database list was empty , we expect api method to be called
        verify(mApiService).getCustomers()
    }

    @Test
    fun notEmptyDatabase_Test() {

        //we want our database to return a not empty list
        //when repository tries to call a data from it
        val dataBaseList: List<CustomerEntity> = listOf(
                CustomerEntity(0, "sd", "asd", 9)
        )
        Mockito.`when`(mCustomersDao.getCustomers())
                .thenReturn(Single.just(dataBaseList))


        //lets make the api return an empty list
        val apiList: List<CustomerModel> = ArrayList()
        Mockito.`when`(mApiService.getCustomers())
                .thenReturn(Single.just(apiList))

        //call the method under test
        mCustomersRepository.getCustomers().subscribe({}, {})


        //make sure database and DAO methods are called
        verify(mCustomersDao).getCustomers()

        //make sure api method is NOT called
        verify(mApiService, never()).getCustomers()
    }
}
