package com.globalkinetic.myweather.networking

import com.globalkinetic.myweather.constants.HOST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyApi {
    var retrofit: RetrofitHelper = retrofit()

    private fun retrofit(): RetrofitHelper {
        val builder = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
        return builder.build().create(RetrofitHelper::class.java)
    }
}