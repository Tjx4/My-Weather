package com.globalkinetic.myweather.features.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.globalkinetic.myweather.extensions.SLIDE_IN_ACTIVITY
import com.globalkinetic.myweather.extensions.navigateToActivity
import com.globalkinetic.myweather.features.previous.PreviousWeatherActivity
import com.globalkinetic.myweather.helpers.*
import com.globalkinetic.myweather.models.Current
import com.globalkinetic.myweather.models.UserLocation
import com.globalkinetic.myweather.models.Weather
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
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
        initViews()
        setSupportActionBar(toolbar)
        checkPlayServicesAndPermission()
    }

    private fun checkPlayServicesAndPermission() {
        checkGoogleApi(this) {
            checkLocationPermissionAndContinue(this) {
                checkGPSAndProceed(this) {
                    isNetworkAvailable(this) {
                        initLocation()
                    }
                }
            }
        }
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
                    checkGPSAndProceed(this){
                        initLocation()
                    }
                } else {
                    onLocationPermissionDenied()
                }
            }
        }
    }

    fun initLocation() {
        locationRequest = LocationRequest()
        locationRequest?.interval = 20000
        locationRequest?.fastestInterval = 1000
        locationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }

                for (location in locationResult.locations) {
                    if (location != null) {
                        onLocationChanged(location)
                    }
                }
            }
        }

        getFusedLocationProviderClient(this).requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )

        val fusedLocationClient = getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location == null) {

                showErrorDialog(
                    this,
                    getString(R.string.unknown_location),
                    getString(R.string.unable_to_get_location),
                    getString(R.string.try_again)
                ) {
                    initLocation()
                }
                return@addOnSuccessListener
            }

            onLocationRequestListenerSuccess(location)
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            showErrorDialog(
                this,
                getString(R.string.location_error),
                getString(R.string.location_retrieve_error),
                getString(R.string.close)
            ) {
                finish()
            }
        }
    }

    fun onLocationRequestListenerSuccess(location: Location?) {
        weatherViewModel.checkAndSetLocation(location)
    }

    override fun onLocationChanged(location: Location?) {

    }

    fun onLocationPermissionDenied() {
        clContent.visibility = View.VISIBLE
        app_bar_layout.visibility = View.VISIBLE

        showErrorDialog(
            this,
            getString(R.string.error),
            getString(R.string.permission_denied, "(Location permission),"),
            getString(R.string.close)
        ) {
            finish()
        }
    }

    private fun addObservers() {
        weatherViewModel.currentLocation.observe(this, Observer { onCurrentLocationSet(it) })
        weatherViewModel.isLocationError.observe(this, Observer { onLocationError(it) })
        weatherViewModel.isWeatherError.observe(this, Observer { onWeatherError(it) })
        weatherViewModel.showLoading.observe(this, Observer { onShowLoading(it) })
        weatherViewModel.weather.observe(this, Observer { onWeatherSet(it) })
        weatherViewModel.hourly.observe(this, Observer { showHourlyWeather(it) })
        weatherViewModel.isWeatherAdded.observe(this, Observer { onWeatherAddToPrevious(it) })
        weatherViewModel.dbError.observe(this, Observer { onSqlitDbError(it) })
    }

    private fun onCurrentLocationSet(location: UserLocation) {
        weatherViewModel.getAndSetWeather(location)
    }

    private fun onShowLoading(isBusy: Boolean) {
        llLoaderContainer.visibility = View.VISIBLE
        clContent.visibility = View.INVISIBLE
        app_bar_layout.visibility = View.INVISIBLE
    }

    private fun onLocationError(isLocationError: Boolean) {
        showErrorDialog(
            this,
            getString(R.string.error),
            getString(R.string.location_error),
            getString(R.string.try_again)
        ) {
            finish()
        }
    }

    private fun onWeatherError(isWeatherError: Boolean) {
        showErrorDialog(
            this,
            getString(R.string.error),
            getString(R.string.weather_error),
            getString(R.string.try_again)
        ) {
            weatherViewModel.currentLocation.value?.let { weatherViewModel.getAndSetWeather(it) }
        }
    }

    private fun onWeatherSet(weather: Weather?) {
        llLoaderContainer.visibility = View.GONE
        clContent.visibility = View.VISIBLE
        app_bar_layout.visibility = View.VISIBLE
    }

    fun initViews() {
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvHourly)
    }

    private fun showHourlyWeather(hourly: List<Current>?) {
        val searchTypeLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvHourly?.layoutManager = searchTypeLayoutManager

        var hourlyAdapter = HourlyAdapter(this, hourly)
        hourlyAdapter.setHourlyClickListener(this)
        rvHourly?.adapter = hourlyAdapter

        rvHourly.visibility = View.VISIBLE
        tvNohourly.visibility = View.GONE
    }

    override fun onHourlyClickListener(view: View, position: Int) {

    }

    fun onWeatherAddToPrevious(isAdded: Boolean) {
        Toast.makeText(this, getString(R.string.added_weather), Toast.LENGTH_SHORT).show()
    }

    fun onSqlitDbError(errorMessage: String) {
        showErrorDialog(this, getString(R.string.db_error), errorMessage, getString(R.string.close)) {}
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {

                showConfirmDialog(this,
                    getString(R.string.confirm),
                    getString(R.string.add_weather_confirm_message),
                    getString(R.string.proceed),
                    getString(R.string.cancel), {
                        weatherViewModel.addWeatherToPreviousList()

                    }, {

                    })
            }
            R.id.action_view_reports -> {
                navigateToActivity(
                    PreviousWeatherActivity::class.java,
                    null,
                    SLIDE_IN_ACTIVITY
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true)
            return super.onKeyDown(keyCode, event)
        }

        return true
    }

}