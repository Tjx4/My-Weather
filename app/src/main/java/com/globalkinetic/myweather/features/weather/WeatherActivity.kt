package com.globalkinetic.myweather.features.weather

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.adapters.HourlyAdapter
import com.globalkinetic.myweather.base.activities.BaseActivity
import com.globalkinetic.myweather.base.activities.BaseMapActivity
import com.globalkinetic.myweather.databinding.ActivityWeatherBinding
import com.globalkinetic.myweather.models.UserLocation
import com.globalkinetic.myweather.models.Weather
import com.google.android.gms.location.LocationListener
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : BaseActivity(), HourlyAdapter.HourlyClickListener {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = WeatherViewModelFactory(application)

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                WeatherViewModel::class.java
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        binding.weatherViewModel = weatherViewModel
        binding.lifecycleOwner = this

        addObservers()

//weatherViewModel.checkAndSetLocation(userLocation)
/*


        if(isGooglePlayServicesAvailable()){
            checkLocationPermissionAndContinue()
        }
        else{
            clContent.visibility = View.GONE
            showErrorAlert(this, getString(R.string.google_play_error), getString(R.string.no_play_services), getString(R.string.ok)) {
                finish()
            }
        }
*/
    }

    private fun addObservers() {
        weatherViewModel.currentLocation.observe(this, Observer { onCurrentLocationSet(it) })
        weatherViewModel.showLoading.observe(this, Observer { onShowLoading(it) })
        weatherViewModel.weather.observe(this, Observer { onWeatherSet(it) })
    }


    private fun onCurrentLocationSet(location: UserLocation) {
        weatherViewModel.getAndSetWeather(location)
    }

    private fun onShowLoading(isBusy: Boolean) {
        avlHeroLoader.visibility = View.VISIBLE
        clContent.visibility = View.INVISIBLE
    }

    private fun onWeatherSet(weather: Weather) {
        avlHeroLoader.visibility = View.GONE
        clContent.visibility = View.VISIBLE

        val searchTypeLayoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        )

        searchTypeLayoutManager.initialPrefetchItemCount =  weather.hourly?.size ?: 0
        rvHourly?.layoutManager = searchTypeLayoutManager
        var hourlyAdapter = HourlyAdapter(this, weather.hourly)
        hourlyAdapter.setHourlyClickListener(this)
        rvHourly?.adapter = hourlyAdapter
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvHourly)
    }

    override fun onHourlyClickListener(view: View, position: Int) {

    }

}