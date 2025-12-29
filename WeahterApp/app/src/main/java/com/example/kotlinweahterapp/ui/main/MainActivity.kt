package com.example.kotlinweahterapp.ui.main

import WeatherAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweahterapp.databinding.ActivityMainBinding
import com.example.kotlinweahterapp.ui.location.LocationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var weatherAdapter: WeatherAdapter

    private val locationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val city = result.data?.getStringExtra("CITY_NAME") ?: "Ankara"
                binding.tvCity.text = city
                viewModel.loadWeather(city)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvNextDays.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        observeData()

        locationLauncher.launch(
            Intent(this, LocationActivity::class.java)
        )
    }

    private fun observeData() {
        viewModel.todayWeather.observe(this) {
            binding.tvDegree.text = "${it.degree}°"
            binding.tvDescription.text = it.description
            binding.tvMinMax.text = "Min ${it.min}° / Max ${it.max}°"
            binding.tvHumidity.text = "Nem: %${it.humidity}"
            binding.tvDate.text = "${it.date} • ${it.day}"
        }

        viewModel.nextDays.observe(this) {
            weatherAdapter = WeatherAdapter(this, it)
            binding.rvNextDays.adapter = weatherAdapter
        }
    }
}
