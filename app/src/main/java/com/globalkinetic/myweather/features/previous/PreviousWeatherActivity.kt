package com.globalkinetic.myweather.features.previous

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.adapters.PreviousWeatherReportsAdapter
import com.globalkinetic.myweather.base.activities.BaseChildActivity
import com.globalkinetic.myweather.databinding.ActivityPreviousWeatherBinding
import com.globalkinetic.myweather.models.Weather
import kotlinx.android.synthetic.main.activity_previous_weather.*
import kotlinx.android.synthetic.main.activity_previous_weather.toolbar

class PreviousWeatherActivity : BaseChildActivity(),
    PreviousWeatherReportsAdapter.PreviousWeatherReportsClickListener {
    private lateinit var binding: ActivityPreviousWeatherBinding
    private lateinit var previousWeatherViewModel: PreviousWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = PreviousWeatherViewModelFactory(application)

        previousWeatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            PreviousWeatherViewModel::class.java
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous_weather)
        binding.previousWeatherViewModel = previousWeatherViewModel
        binding.lifecycleOwner = this

        addObservers()

        toolbar?.setNavigationOnClickListener { onBackPressed() }
        setSupportActionBar(toolbar)
    }

    private fun addObservers() {
        previousWeatherViewModel.previousWeatherReports.observe(this, Observer {onPreviousReportsSet(it)})
        previousWeatherViewModel.showLoading.observe(this, Observer { onShowLoading(it) })
        previousWeatherViewModel.isNoPrevious.observe(this, Observer { onNoReportsFound(it) })
    }

    private fun onPreviousReportsSet(previousReports: List<Weather>) {
        val previousReportsLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        previousReportsLayoutManager.initialPrefetchItemCount = previousReports?.size ?: 0
        rvPreviousReports?.layoutManager = previousReportsLayoutManager
        var hourlyAdapter = PreviousWeatherReportsAdapter(this, previousReports)
        hourlyAdapter.setPreviousWeatherReportsClickListener(this)
        rvPreviousReports?.adapter = hourlyAdapter

        avPrevLoader.visibility = View.GONE
    }

    private fun onShowLoading(isBusy: Boolean) {
        avPrevLoader.visibility = View.VISIBLE

    }

    private fun onNoReportsFound(isNoReports: Boolean) {
         tvNoPrevious.visibility = View.VISIBLE
        avPrevLoader.visibility = View.GONE
    }

    override fun onPreviousWeatherReportsClickListener(view: View, position: Int) {

    }

}