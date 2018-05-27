package com.example.yanbraslavsky.restaurantreservations.di.app

import com.example.yanbraslavsky.restaurantreservations.App
import com.example.yanbraslavsky.restaurantreservations.di.AppModule
import com.example.yanbraslavsky.restaurantreservations.screens.customers.CustomersContract
import com.example.yanbraslavsky.restaurantreservations.repositories.customers.CustomersRepository
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainContract
import com.example.yanbraslavsky.restaurantreservations.screens.main.MainPresenter
import com.example.yanbraslavsky.restaurantreservations.screens.reservation.ReservationContract
import com.example.yanbraslavsky.restaurantreservations.repositories.reservations.ReservationsRepository
import dagger.Module
import dagger.Provides

@Module
class TestAppModule(mApp: App) : AppModule(mApp) {

    //we leave a possibility to change that presenter before the injection
    var mMockedMainPresenter: MainContract.Presenter? = null
    var mMockedCustomerPresenter: CustomersContract.Presenter? = null
    var mMockedReservationsPresenter: ReservationContract.Presenter? = null


    @Provides
    override fun provideMainPresenter(): MainContract.Presenter {
        return mMockedMainPresenter ?: MainPresenter()
    }

    @Provides
    override fun provideCustomersPresenter(customersRepository: CustomersRepository): CustomersContract.Presenter {
        return mMockedCustomerPresenter ?: super.provideCustomersPresenter(customersRepository)
    }

    @Provides
    override fun provideReservationPresenter(reservationsRepository: ReservationsRepository): ReservationContract.Presenter {
        return mMockedReservationsPresenter ?: super.provideReservationPresenter(reservationsRepository)
    }

}