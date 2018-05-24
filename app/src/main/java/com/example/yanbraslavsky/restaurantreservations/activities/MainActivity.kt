package com.example.yanbraslavsky.restaurantreservations.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.api.RestarauntService
import com.example.yanbraslavsky.restaurantreservations.api.models.responses.CustomerModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    protected val mDisposablesBag: CompositeDisposable = CompositeDisposable()
    val mApiService: RestarauntService = RestarauntService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn.setOnClickListener({

            fetchData()


//            CustomerSelectionActivity.open(this)


        })
    }


    private fun fetchData() {
//        mBoundView?.showLoading()
        mDisposablesBag.add(
                mApiService.getCustomers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally({ /*mBoundView?.stopLoading()*/ })
                        .subscribe(
                                { result ->
                                    // DO shit
                                    result.forEach({
                                        Log.d("yan", "${it.customerFirstName}")
                                    })

                                },
                                { error ->
                                    //report error shit
                                    Log.d("yan", "$error")
                                }
                        )
        )
    }
}