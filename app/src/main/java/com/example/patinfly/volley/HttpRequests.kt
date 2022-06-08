package com.example.patinfly.volley

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.example.patinfly.repositories.AssetsProvider

@SuppressLint("NotifyDataSetChanged")
class HttpRequests(val context: Context) {

    companion object {

        private const val testScooter = "ea1eb2ba-d480-11ec-91c7-ecf4bbcc40f8"

        fun rentStart(context: Context, successListener: StartRentListener, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/start/$scooterUuid"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            Log.d("VOLLEY START RENT", url)
            val stringRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(stringRequest)
        }

        fun rentStop(context: Context, successListener: StopRentListener, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/stop/$scooterUuid"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            val stringRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(stringRequest)
        }

        fun getRents(context: Context, successListener: GetRentsListener) {
            val url = "https://patinfly.com/endpoints/rent"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            val stringRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(stringRequest)
        }

        fun getScooters(context: Context, successListener: GetScootersListener) {
            val url = "https://patinfly.com/endpoints/scooter"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            val stringRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(stringRequest)
        }

        fun getScooterState(context: Context, successListener: GetScooterListener, scooterUuid: String = testScooter){
            val url = "https://patinfly.com/endpoints/scooter/$scooterUuid"
            val api_key  = getApiKey(context)
            val errorListener = GenericErrorListener(context)
            val stringRequest = CustomStringRequest(
                Request.Method.GET, url, successListener, errorListener, api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(stringRequest)
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