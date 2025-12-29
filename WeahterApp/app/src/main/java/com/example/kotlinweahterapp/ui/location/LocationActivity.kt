package com.example.kotlinweahterapp.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlinweahterapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.Locale

class LocationActivity : AppCompatActivity() {

    private lateinit var flpc: FusedLocationProviderClient
    private lateinit var locationTask: Task<android.location.Location>

    private val LOCATION_PERMISSION_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        flpc = LocationServices.getFusedLocationProviderClient(this)
        izinKontrol()
    }

    private fun izinKontrol() {
        val izin = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (izin != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        } else {
            locationTask = flpc.lastLocation
            konumAl()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            locationTask = flpc.lastLocation
            konumAl()
        } else {
            sonucuGonder("İzin verilmedi")
        }
    }

    @SuppressLint("MissingPermission")
    private fun konumAl() {
        locationTask.addOnSuccessListener { location ->
            if (location != null) {
                sehirBul(location.latitude, location.longitude)
            } else {
                sonucuGonder("Konum alınamadı")
            }
        }
    }

    private fun sehirBul(lat: Double, lon: Double) {
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val adresler = geocoder.getFromLocation(lat, lon, 1)

            val city =
                adresler?.firstOrNull()?.locality
                    ?: adresler?.firstOrNull()?.adminArea
                    ?: "Bilinmiyor"

            sonucuGonder(city)

        } catch (e: Exception) {
            sonucuGonder("Şehir bulunamadı")
        }
    }

    private fun sonucuGonder(city: String) {
        val intent = Intent()
        intent.putExtra("CITY_NAME", city)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
