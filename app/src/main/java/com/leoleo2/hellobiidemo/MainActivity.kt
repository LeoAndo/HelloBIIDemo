package com.leoleo2.hellobiidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        D/MainActivity: name: Googleplex
        D/MainActivity: address: 1600 Amphitheatre Pkwy, Mountain View, CA 94043
        D/MainActivity: disambiguatingDescription: valet
        D/MainActivity: latitude: 37.3861
        D/MainActivity: longitude: -122.084
         */
        Log.d(TAG, "data: ${intent?.data}")
        intent?.extras?.let {
            handleBuiltInIntents(it)
        }
    }

    private fun handleBuiltInIntents(it: Bundle) {
        val type = it.getString("type") ?: return
        Log.d(TAG, "type: $type")

        // log出力
        when (type) {
            BII_TYPE_GET_CHARGING_STATION -> {
                Log.d(TAG, "name: ${it.getString("name")}")
                Log.d(TAG, "address: ${it.getString("address")}")
                Log.d(TAG, "latitude: ${it.getString("latitude")}")
                Log.d(TAG, "longitude: ${it.getString("longitude")}")
            }
            BII_TYPE_GET_PARKING_FACILITY -> {
                Log.d(TAG, "name: ${it.getString("name")}")
                Log.d(TAG, "address: ${it.getString("address")}")
                Log.d(
                    TAG,
                    "disambiguatingDescription: ${it.getString("disambiguatingDescription")}"
                )
                Log.d(TAG, "latitude: ${it.getString("latitude")}")
                Log.d(TAG, "longitude: ${it.getString("longitude")}")
            }
        }

        // Map起動
        val latitude = it.getString("latitude") ?: return
        val longitude = it.getString("latitude") ?: return
        val name = it.getString("name") ?: return
        AppLaunchHelper(this).launchMapApp(latitude.toDouble(), longitude.toDouble(), name)
    }

    companion object {
        private const val TAG = "MainActivity"

        // Built-in Intents
        private const val BII_TYPE_GET_CHARGING_STATION = "ChargingStation"
        private const val BII_TYPE_GET_PARKING_FACILITY = "ParkingFacility"
    }
}