package com.globalkinetic.myweather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.models.Current

class HourlyAdapter(context: Context, private val hourly: List<Current>?) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var hourlyClickListener: HourlyClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.hourly_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchType = hourly?.get(position)

        holder.nameTv.text = searchType?.clouds.toString()
        holder.cloudsTv.text = searchType?.clouds.toString()
        holder.tempTv.text = searchType?.temp.toString()
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var nameTv: TextView = itemView.findViewById(R.id.tvTime)
        internal var cloudsTv: TextView = itemView.findViewById(R.id.tvClouds)
        internal var tempTv: TextView = itemView.findViewById(R.id.tvTemp)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            hourlyClickListener?.onHourlyClickListener(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): Current? {
        return hourly?.get(id) ?: null
    }

    fun setHourlyClickListener(hourlyClickListener: HourlyClickListener) {
        this.hourlyClickListener = hourlyClickListener
    }

    interface HourlyClickListener {
        fun onHourlyClickListener(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return hourly?.size ?: 0
    }
}