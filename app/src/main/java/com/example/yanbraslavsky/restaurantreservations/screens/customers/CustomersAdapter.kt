package com.example.yanbraslavsky.restaurantreservations.screens.customers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yanbraslavsky.restaurantreservations.R
import com.example.yanbraslavsky.restaurantreservations.database.enteties.CustomerEntity


class CustomersAdapter(private val mDataItems: List<CustomerEntity>,
                       private val mListener: ((CustomerEntity) -> Unit)?) :
        RecyclerView.Adapter<CustomersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.customer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mDataItems[position]
        holder.mItem?.let { item ->

            //update question title
            holder.mFirstName.text = item.customerFirstName
            holder.mLastName.text = item.customerLastName
            holder.itemView.setOnClickListener({
                mListener?.invoke(item)
            })
        }
    }

    override fun getItemCount(): Int {
        return mDataItems.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mFirstName: TextView = mView.findViewById(R.id.firstName)
        val mLastName: TextView = mView.findViewById(R.id.lastName)
        var mItem: CustomerEntity? = null
    }
}