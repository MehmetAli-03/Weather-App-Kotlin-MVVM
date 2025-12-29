package com.example.kotlinweahterapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinweahterapp.data.model.WeatherModel
import com.example.kotlinweahterapp.data.model.WeatherResponse
import com.example.kotlinweahterapp.data.repository.WeatherRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val repository = WeatherRepository()

    val todayWeather = MutableLiveData<WeatherModel>()
    val nextDays = MutableLiveData<List<WeatherModel>>()
    val error = MutableLiveData<String>()

    fun loadWeather(city: String) {
        repository.getWeather(city)
            .enqueue(object : Callback<List<WeatherModel>> {

                override fun onResponse(
                    call: Call<List<WeatherModel>>,
                    response: Response<List<WeatherModel>>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body() ?: return
                        todayWeather.value = list.first()
                        nextDays.value = list.drop(1).take(5)
                    }
                }

                override fun onFailure(call: Call<List<WeatherModel>>, t: Throwable) {
                    error.value = t.message
                }
            })
    }
}

