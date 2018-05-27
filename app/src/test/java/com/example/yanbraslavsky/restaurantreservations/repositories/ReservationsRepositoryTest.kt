package com.example.yanbraslavsky.restaurantreservations.repositories

import com.affinitas.task.utils.RxUtils
import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.database.daos.TableDao
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import com.example.yanbraslavsky.restaurantreservations.repositories.reservations.ReservationsRepository
import com.example.yanbraslavsky.restaurantreservations.repositories.reservations.ReservationsRepositoryImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ReservationsRepositoryTest {

    private lateinit var mReservationsRepository: ReservationsRepository
    private lateinit var mApiService: RestaurantService
    private lateinit var mRestaurantDatabase: RestaurantDatabase
    private lateinit var mTablesDao: TableDao

    @Before
    fun setUp() {

        //We are not really interested in testing multithreaded loading at this point
        //So we just constrain all Rx operations for a single thread
        RxUtils.makeRxSchedulersImmediate()

        mTablesDao = mock()
        mApiService = mock()
        mRestaurantDatabase = mock()
        mReservationsRepository =
                ReservationsRepositoryImpl(mApiService, mRestaurantDatabase)

        Mockito.`when`(mRestaurantDatabase.tableDao())
                .thenReturn(mTablesDao)
    }

    @After
    fun tearDown() {
        RxUtils.resetRxSchedulers()
    }

    @Test
    fun emptyDatabase_Test() {

        //we want our database to return an empty list
        //when repository tries to call a data from it
        val dataBaseList: ArrayList<TableEntity> = ArrayList()
        Mockito.`when`(mTablesDao.getTables())
                .thenReturn(Single.just(dataBaseList))


        //lets make the api return an empty list as well
        val apiList: List<Boolean> = ArrayList()
        Mockito.`when`(mApiService.getTables())
                .thenReturn(Single.just(apiList))

        //call the method under test
        mReservationsRepository.getTables().subscribe({}, {})


        //make sure database and DAO methods are called
        verify(mTablesDao).getTables()


        //since database list was empty , we expect api method to be called
        verify(mApiService).getTables()
    }

    @Test
    fun notEmptyDatabase_Test() {

        //we want our database to return a not empty list
        //when repository tries to call a data from it
        val dataBaseList: List<TableEntity> = listOf(
                TableEntity(0, false, 3)
        )

        Mockito.`when`(mTablesDao.getTables())
                .thenReturn(Single.just(dataBaseList))


        //lets make the api return an empty list
        val apiList: List<Boolean> = ArrayList()
        Mockito.`when`(mApiService.getTables())
                .thenReturn(Single.just(apiList))

        //call the method under test
        mReservationsRepository.getTables().subscribe({}, {})


        //make sure database and DAO methods are called
        verify(mTablesDao).getTables()

        //make sure api method is NOT called
        verify(mApiService, never()).getTables()
    }

    @Test
    fun updateTable_Test() {

        //call method under test with a fake table
        val table = TableEntity(0, false, 3)
        mReservationsRepository.updateTable(table).subscribe({}, {})

        //make sure DAO method is called with the table provided
        verify(mTablesDao).update(table)

    }


}
