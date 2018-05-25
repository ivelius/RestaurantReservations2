package com.example.yanbraslavsky.restaurantreservations.api

import com.example.yanbraslavsky.restaurantreservations.BuildConfig
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.TableModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestarauntService {

    @GET("/quandoo-assessment/customer-list.json")
    fun getCustomers(): Observable<List<CustomerModel>>

    @GET("/quandoo-assessment/table-map.json")
    fun getTables(): Observable<List<TableModel>>

    companion object {

        private val BASE_URL = "https://s3-eu-west-1.amazonaws.com/"

        fun create(): RestarauntService {
            val httpClient = createHttpClient()
            val retrofit = Retrofit.Builder()
                    .client(httpClient.build())

                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(RestarauntService::class.java)
        }

        private fun createHttpClient(): OkHttpClient.Builder {
            val httpClient = OkHttpClient.Builder()
            val logging = createLoggingInterceptor()
            // add logging as last interceptor for better debugging
            httpClient.addInterceptor(logging)
            return httpClient
        }

        private fun createLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return logging
        }
    }
}

