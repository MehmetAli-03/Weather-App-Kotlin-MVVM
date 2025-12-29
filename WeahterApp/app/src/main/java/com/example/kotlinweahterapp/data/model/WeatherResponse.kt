package com.example.kotlinweahterapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("result")
    @Expose
    var result: List<WeatherModel>
)
