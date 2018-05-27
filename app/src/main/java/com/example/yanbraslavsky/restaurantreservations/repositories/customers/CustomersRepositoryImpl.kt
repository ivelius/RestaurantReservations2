package com.example.yanbraslavsky.restaurantreservations.repositories.customers

import com.example.yanbraslavsky.restaurantreservations.api.RestaurantService
import com.example.yanbraslavsky.restaurantreservations.database.RestaurantDatabase
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.repositories.BaseRepository
import io.reactivex.Single
import javax.inject.Inject

class CustomersRepositoryImpl
@Inject constructor(private val mApiService: RestaurantService,
                    private val mRestaurantDatabase: RestaurantDatabase) : BaseRepository<List<CustomerEntity>>(), CustomersRepository {


    override fun getCustomers(): Single<List<CustomerEntity>> {
        return super.getResource()
    }

    override fun databaseObservable(): Single<List<CustomerEntity>> {
        return Single.create {
            val list = mRestaurantDatabase.customerDao().getCustomers().blockingGet()
            if (list.isEmpty()) {
                it.onError(Error("Empty List"))
            } else {
                it.onSuccess(list)
            }
        }
    }

    override fun apiObservable(): Single<List<CustomerEntity>> {
        return Single.create {
            val list = mApiService.getCustomers().blockingGet()
            if (list == null || list.isEmpty()) {
                it.onError(Error("Error"))
            } else {

                list.forEach({
                    val customer = CustomerEntity(0, it.customerFirstName,
                            it.customerLastName, it.id)
                    mRestaurantDatabase.customerDao().insert(customer)
                })

                it.onSuccess(mRestaurantDatabase.customerDao().getCustomers().blockingGet())
            }
        }
    }
}