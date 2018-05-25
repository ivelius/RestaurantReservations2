package com.example.yanbraslavsky.restaurantreservations.mvp

import java.io.Serializable

/**
 * Created by yan.braslavski on 10/26/17.
 */
object BaseContract {

    /**
     * Here we define the communication :
     * Presenter -> View
     *
     * Note , I assume in this project there will always be a necessity to use those base methods for every view.
     * Otherwise I wouldn't couple them in base interface
     */
    interface View {
        fun showMessage(message: String)
        fun showLoading()
        fun stopLoading()
        fun changeTitle(title: String)
        fun showError(errorMessage: String)
    }

    /**
     * Here we define the communication :
     * View -> Presenter
     */
    interface Presenter<in T : View> : Serializable {
        fun bind(view: T)
        fun unbind()
    }
}