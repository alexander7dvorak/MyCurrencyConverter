package com.currencyconverter.main

import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import android.graphics.Color
import android.view.View
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.currencyconverter.data.models.Country





class CommonAdapter(
    context: Context,
    private val data: MutableList<Country>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CommonAdapter.RecycleViewHolder>() {
    private val mContext: Context

    init {
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        return RecycleViewHolder(TextView(mContext))
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val country = data[position]
        holder.textView.setBackgroundColor(Color.WHITE)
        holder.textView.text = data[position].getCountryCurrency()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var textView: TextView
        var color: Int = Color.WHITE

        init {
            textView = itemView as TextView
            textView.setOnClickListener(this)
        }

        fun bind(value: Country, isActivated: Boolean = false) {
            textView.text = value.getCountryCurrency()
            itemView.isActivated = isActivated
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            textView.setBackgroundColor(Color.BLUE)
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

    }
}