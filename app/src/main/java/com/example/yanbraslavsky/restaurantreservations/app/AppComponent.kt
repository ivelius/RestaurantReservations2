package com.example.yanbraslavsky.restaurantreservations.app

import com.example.yanbraslavsky.restaurantreservations.screens.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}