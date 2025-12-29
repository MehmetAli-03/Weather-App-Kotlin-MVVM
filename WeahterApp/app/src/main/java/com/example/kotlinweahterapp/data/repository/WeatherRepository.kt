package com.example.kotlinweahterapp.data.repository

import com.example.kotlinweahterapp.data.remote.RetrofitClient

class WeatherRepository {

    fun getWeather(city: String) =
        RetrofitClient.api.getWeather("tr", city)
}

