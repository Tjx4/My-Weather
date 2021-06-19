package com.globalkinetic.myweather.base.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseVieModel(app: Application) : AndroidViewModel(app){
}