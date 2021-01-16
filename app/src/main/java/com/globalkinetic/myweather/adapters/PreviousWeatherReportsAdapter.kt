package com.globalkinetic.myweather.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.helpers.getFormatedDate
import com.globalkinetic.myweather.models.Weather

class PreviousWeatherReportsAdapter(context: Context, private val previousWeatherReports: List<Weather>?) : RecyclerView.Adapter<PreviousWeatherReportsAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var previousWeatherReportsClickListener: PreviousWeatherReportsClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.previous_weather_reports_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val previousWeatherReport = previousWeatherReports?.get(position)

        holder.locationNameTv.text = previousWeatherReport?.locationName
        holder.dateTimeTv.text = getFormatedDate(previousWeatherReport?.current?.dt ?: 0)
        holder.descriptionTv.text = previousWeatherReport?.current?.weather?.get(0)?.description
        holder.precipTv.text = "${previousWeatherReport?.current?.humidity}%"
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var locationNameTv: TextView = itemView.findViewById(R.id.tvLocationName)
        internal var dateTimeTv: TextView = itemView.findViewById(R.id.tvDateTime)
        internal var descriptionTv: TextView = itemView.findViewById(R.id.tvDescription)
        internal var precipTv: TextView = itemView.findViewById(R.id.tvPrecip)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            previousWeatherReportsClickListener?.onPreviousWeatherReportsClickListener(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): Weather? {
        return previousWeatherReports?.get(id) ?: null
    }

    fun setPreviousWeatherReportsClickListener(previousWeatherReportsClickListener: PreviousWeatherReportsClickListener) {
        this.previousWeatherReportsClickListener = previousWeatherReportsClickListener
    }

    interface PreviousWeatherReportsClickListener {
        fun onPreviousWeatherReportsClickListener(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return previousWeatherReports?.size ?: 0
    }
}