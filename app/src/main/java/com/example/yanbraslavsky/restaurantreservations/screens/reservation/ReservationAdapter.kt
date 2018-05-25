package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.yanbraslavsky.restaurantreservations.R


class ReservationAdapter(private val mDataItems: List<Boolean>,
                         private val mListener: ((Boolean) -> Unit)?) :
        RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.table_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mDataItems[position]
        holder.mItem?.let { item ->

            holder.mImageButton.isEnabled = item
            if (holder.mImageButton.isEnabled) {
                holder.mImageButton.alpha = 1.0F
            } else {
                holder.mImageButton.alpha = 0.3F
            }

            holder.mImageButton.setOnClickListener({
                mListener?.invoke(item)
                holder.mImageButton.isSelected = !holder.mImageButton.isSelected
            })
        }
    }

    override fun getItemCount(): Int {
        return mDataItems.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageButton: ImageButton = mView.findViewById(R.id.imageButton)
        var mItem: Boolean? = null
    }
}