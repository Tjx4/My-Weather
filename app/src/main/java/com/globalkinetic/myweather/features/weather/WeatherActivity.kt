package com.globalkinetic.myweather.features.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.adapters.HourlyAdapter
import com.globalkinetic.myweather.base.activities.BaseActivity
import com.globalkinetic.myweather.databinding.ActivityWeatherBinding
import com.globalkinetic.myweather.helpers.showErrorAlert
import com.globalkinetic.myweather.models.UserLocation
import com.globalkinetic.myweather.models.Weather
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : BaseActivity(), LocationListener, HourlyAdapter.HourlyClickListener {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var weatherViewModel: WeatherViewModel

    private var locationRequest: LocationRequest? = null

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

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initLocation()
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
            )
        }
    }

    fun initLocation() {
        locationRequest = LocationRequest()
        locationRequest?.interval = 2000
        locationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) { return }

                for (location in locationResult.locations) {
                    onLocationChanged(location)
                }
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                onRequestListenerSuccess(location)
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            }
            else{
                showErrorAlert(this, "Location Error", "Error retrieving location", getString(R.string.close)) {

                }
            }
        }

    }

    fun onRequestListenerSuccess(location: Location?) {
        weatherViewModel.checkAndSetLocation(location)
    }

    override fun onLocationChanged(location: Location?) {
        val dfdf = location
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (i in permissions.indices) {
            val permission = permissions[i]
            val grantResult = grantResults[i]

            if (permission == Manifest.permission.ACCESS_FINE_LOCATION) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    initLocation()
                } else {
                    onLocationPermissionDenied()
                }
            }
        }
    }

    fun onLocationPermissionDenied() {
        clContent.visibility = View.INVISIBLE
        showErrorAlert(this, getString(R.string.error), getString(R.string.permission_denied, "(Location permission),"), getString(R.string.close)) {
            finish()
        }
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