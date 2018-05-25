package com.example.yanbraslavsky.restaurantreservations.screens.reservation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.yanbraslavsky.restaurantreservations.R


class ReservationAdapter(private val mDataItems: List<ReservationContract.GridCellTableModel>,
                         private val mListener: ((ReservationContract.GridCellTableModel) -> Unit)?) :
        RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.table_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mDataItems[position]
        holder.mItem?.let { item ->

            //this table might be reserved by this customer , so we will give him a
            //possibility to change his reservation by showing this table as "available"
            //and selected
            holder.mImageButton.isEnabled = item.mTableEntity.available && !item.reservedByOther
            holder.mImageButton.isSelected = item.mSelected
            if (holder.mImageButton.isEnabled) {
                holder.mImageButton.alpha = 1.0F
            } else {
                holder.mImageButton.alpha = 0.3F
            }

            holder.mImageButton.setOnClickListener({
                mListener?.invoke(item)
            })
        }
    }

    override fun getItemCount(): Int {
        return mDataItems.size
    }

    fun updateTable(tableItem: ReservationContract.GridCellTableModel) {
        notifyItemChanged(mDataItems.indexOfFirst {
            it.mTableEntity.tableNumber == tableItem.mTableEntity.tableNumber
        })
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageButton: ImageButton = mView.findViewById(R.id.imageButton)
        var mItem: ReservationContract.GridCellTableModel? = null
    }
}