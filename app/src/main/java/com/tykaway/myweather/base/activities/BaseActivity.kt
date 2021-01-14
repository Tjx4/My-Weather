package com.tykaway.myweather.base.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.tykaway.myweather.constants.ACTIVITY_TRANSITION
import com.tykaway.myweather.constants.PAYLOAD_KEY

abstract class BaseActivity : AppCompatActivity() {
    var isNewActivity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTransitions(this)
        isNewActivity = true
    }

    protected fun hideKeyboard(view: View){
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initTransitions(activity: Activity) {
        try {
            val activityTransition = activity.intent.getBundleExtra(PAYLOAD_KEY)?.getIntArray(ACTIVITY_TRANSITION)
            activity.overridePendingTransition(activityTransition!![0], activityTransition[1])
        }
        catch (e: Exception) {
            Log.e("AT", "$e")
        }
    }


}