package com.globalkinetic.myweather.base.activities

import android.os.Bundle
import android.view.MenuItem
import com.globalkinetic.myweather.extensions.SLIDE_OUT_ACTIVITY

abstract class BaseChildActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayUseLogoEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        overridePendingTransition(SLIDE_OUT_ACTIVITY.inAnimation, SLIDE_OUT_ACTIVITY.outAnimation)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(SLIDE_OUT_ACTIVITY.inAnimation, SLIDE_OUT_ACTIVITY.outAnimation)
    }

}