package com.example.kotlinweahterapp.data.remote

import com.example.kotlinweahterapp.data.model.WeatherModel
import com.example.kotlinweahterapp.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @Headers(
        "content-type: application/json",
        "authorization: apikey 5OEbAbEyGSbfOewqVaoEPR:3i2qv5XtPxn9DVzXIErbYx"
    )
    @GET("weather/getWeather")
    fun getWeather(
        @Query("lang") lang: String,
        @Query("city") city: String
    ): Call<List<WeatherModel>>
}



