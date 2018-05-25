package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity
import com.example.yanbraslavsky.restaurantreservations.mvp.BasePresenter
import javax.inject.Inject


open class ReservationPresenter @Inject constructor(private val mReservationUseCase: ReservationUseCase)
    : BasePresenter<ReservationContract.View>(), ReservationContract.Presenter {

    private var mData: List<ReservationContract.GridCellTableModel>? = null
    private var mCustomer: CustomerEntity? = null

    override fun bind(view: ReservationContract.View) {
        super.bind(view)

        //if data already exists we show it without fetching
        mData?.let {
            showData(it)
            return
        }

        fetchData()
    }


    private fun showData(data: List<ReservationContract.GridCellTableModel>) {
        mBoundView?.showTables(data)
    }

    private fun fetchData() {
        mBoundView?.showLoading()
        mDisposablesBag.add(
                mReservationUseCase.getRemoteTablesList()
                        //we transform the entities by adding
                        //the selection representation and wrapping into GridCellTableModel
                        .map {
                            return@map it.map {
                                val selected = mCustomer?.customerId == it.reservedByCustomerId
                                return@map ReservationContract.GridCellTableModel(it, selected)
                            }
                        }
                        .doFinally({ mBoundView?.stopLoading() })
                        .subscribe(
                                { result ->
                                    mBoundView?.let {
                                        mData = result
                                        showData(result)
                                    }
                                },
                                { error ->
                                    mBoundView?.let {
                                        it.showError(wrapErrorMessage(error))
                                    }
                                }
                        )
        )
    }

    override fun onTableItemClick(tableItem: ReservationContract.GridCellTableModel) {
        //we update the selection status in the collection of tables
        //and assigning to the selection the userID that reserved the table
        mData?.find { tableItem.mTableEntity.tableNumber == it.mTableEntity.tableNumber }?.let {
            it.mSelected = !it.mSelected
            mBoundView?.updateTable(it)
        }
    }

    override fun unbind() {
        super.unbind()
        // Now when user is finished with the selection
        // we can update the database
        mData?.let {
            it.map {
                //we want to update the state of this table
                //and mark it as reserved for current customer
                if (it.mSelected) {
                    it.mTableEntity.reservedByCustomerId = mCustomer?.customerId
                    mReservationUseCase.updateTable(it.mTableEntity)
                }
                return@map it.mTableEntity
            }
        }
    }

    override fun setCustomer(customer: CustomerEntity) {
        mCustomer = customer
    }

    private fun wrapErrorMessage(error: Throwable) = error.message ?: "Something is wrong :("
}