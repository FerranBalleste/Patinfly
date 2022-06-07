package com.example.patinfly.volley

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.android.volley.Request
import com.example.patinfly.R
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.ScooterParser
import com.example.patinfly.model.VolleyRentParser
import com.example.patinfly.persitence.RentDao
import com.example.patinfly.persitence.ScooterDao
import com.example.patinfly.repositories.AssetsProvider
import com.google.gson.JsonParser

@SuppressLint("NotifyDataSetChanged")
class HttpRequests(val context: Context) {

    companion object {

        private const val testScooter = "ea1eb2ba-d480-11ec-91c7-ecf4bbcc40f8"

        fun rentStart(context: Context, successListener: StartRentListener, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/start/$scooterUuid"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            Log.d("VOLLEY START RENT", url)
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun rentStop(context: Context, successListener: StopRentListener, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/stop/$scooterUuid"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun getRents(context: Context, successListener: GetRentsListener) {
            val url = "https://patinfly.com/endpoints/rent"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun getScooters(context: Context, successListener: GetScootersListener) {
            val url = "https://patinfly.com/endpoints/scooter"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        private fun getApiKey(context: Context): String{
            val key = AssetsProvider.getJsonDataFromRawAsset(context, "token")
            key?.let { return key }
            ?: run {
                Log.e("VOLLEY TOKEN", "User api key missing!!, add token.txt file at raw folder")
                return "NO KEY"
            }
        }
    }

}