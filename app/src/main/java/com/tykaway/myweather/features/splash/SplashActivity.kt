package com.tykaway.myweather.features.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tykaway.myweather.extensions.FADE_IN_ACTIVITY
import com.tykaway.myweather.extensions.navigateToActivity
import com.tykaway.myweather.features.weather.WeatherActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToActivity(
            WeatherActivity::class.java,
            null,
            FADE_IN_ACTIVITY
        )
    }
}

