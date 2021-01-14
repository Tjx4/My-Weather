package com.tykaway.myweather.helpers

import com.tykaway.myweather.constants.HOST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyApi {
    var retrofitHelper: RetrofitHelper = retrofit()

    private fun retrofit(): RetrofitHelper {
        val builder = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        return retrofit.create(RetrofitHelper::class.java)
    }
}