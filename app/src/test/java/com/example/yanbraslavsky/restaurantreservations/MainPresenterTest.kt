package com.example.yanbraslavsky.restaurantreservations

import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainPresenterTest {

    private lateinit var mMainPresenter: MainPresenter
    private lateinit var mMainView: MainContract.View

    @Before
    fun setUp() {
        mMainPresenter = MainPresenter()
        mMainView = mock()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun startButtonClicked_Test() {

        mMainPresenter.bind(mMainView)
        mMainPresenter.startButtonClicked()

        //make sure presenter tells the view to show the next screen
        verify(mMainView).showCustomersScreen()
    }
}
