package com.example.patinfly.volley

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.android.volley.Request
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.ScooterParser
import com.example.patinfly.model.VolleyRentParser
import com.example.patinfly.persitence.RentDao
import com.example.patinfly.persitence.ScooterDao
import com.google.gson.JsonParser

@SuppressLint("NotifyDataSetChanged")
class HttpRequests {

    companion object {

        private const val api_key = "0NoXsbyfOczMKkS1qrZgxBEiwlIFvT87DpYPm6ed"
        private const val testScooter = "ea1eb2ba-d480-11ec-91c7-ecf4bbcc40f8"
        private val errorListener = GenericErrorListener()

        fun rentStart(context: Context, successListener: StartRentListener, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/start/$scooterUuid"
            Log.d("VOLLEY START RENT", url)
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun rentStop(context: Context, successListener: StopRentListener, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/stop/$scooterUuid"
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun getRents(context: Context, successListener: GetRentsListener) {
            val url = "https://patinfly.com/endpoints/rent"
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun getScooters(context: Context, successListener: GetScootersListener) {
            val url = "https://patinfly.com/endpoints/scooter"
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }
    }

}