package com.tykaway.myweather.extensions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tykaway.myweather.R
import com.tykaway.myweather.constants.ACTIVITY_TRANSITION
import com.tykaway.myweather.constants.PAYLOAD_KEY

val SLIDE_IN_ACTIVITY = getTransitionAnimation(R.anim.slide_right, R.anim.no_transition)
val SLIDE_OUT_ACTIVITY =  getTransitionAnimation(R.anim.no_transition, R.anim.slide_left)
val FADE_IN_ACTIVITY = getTransitionAnimation(R.anim.fade_in, R.anim.no_transition)
val FADE_OUT_ACTIVITY = getTransitionAnimation(R.anim.no_transition, R.anim.fade_out)

private fun getTransitionAnimation(inAnimation: Int, outAnimation: Int): Transition {
    val transitionProvider = Transition()
    transitionProvider.inAnimation = inAnimation
    transitionProvider.outAnimation = outAnimation
    return transitionProvider
}

fun AppCompatActivity.navigateToActivity(
    className: String,
    payload: Bundle?,
    transitionAnimation: Transition
) {
    goToActivity(className, transitionAnimation, payload)
}

fun AppCompatActivity.navigateToActivity(
    activity: Class<*>,
    payload: Bundle?,
    transitionAnimation: Transition
) {
    goToActivity(activity, transitionAnimation, payload)
}

private fun AppCompatActivity.goToActivity(
    activity: Class<*>,
    transitionAnimation: Transition,
    payload: Bundle?
) {
    val intent = Intent(this, activity)

    val fullPayload = payload ?: Bundle()
    fullPayload.putIntArray(
        ACTIVITY_TRANSITION, intArrayOf(
            transitionAnimation.inAnimation,
            transitionAnimation.outAnimation
        )
    )

    intent.putExtra(PAYLOAD_KEY, fullPayload)
    startActivity(intent)
}

private fun AppCompatActivity.goToActivity(
    className: String,
    transitionAnimation: Transition,
    payload: Bundle?
) {

    val intent = Intent(
        this,
        Class.forName(className)
    )

    val fullPayload = payload ?: Bundle()
    fullPayload.putIntArray(
        ACTIVITY_TRANSITION, intArrayOf(
            transitionAnimation.inAnimation,
            transitionAnimation.outAnimation
        )
    )

    intent.putExtra(PAYLOAD_KEY, fullPayload)
    startActivity(intent)

}

data class Transition(var inAnimation: Int = 0, var outAnimation: Int = 0)

