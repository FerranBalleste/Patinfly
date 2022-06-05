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

        @SuppressLint("SetTextI18n")
        fun rentStart(context: Context, navController: NavController, action: NavDirections, textView: TextView, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/start/$scooterUuid"
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url,
                { response ->
                    when (getCode(response)) {
                        200 -> navController.navigate(action)
                        405 -> textView.text = "Error, Scooter already on rent!!!"
                    }
                },
                errorListener,
                api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun rentStop(context: Context, navController: NavController?, scooterUuid: String = testScooter) {
            val url = "https://patinfly.com/endpoints/rent/stop/$scooterUuid"
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url,
                { response ->
                    when (getCode(response)) {
                        200 -> {navController?.popBackStack(); Log.d("VOLLEY RESPONSE", "Stopped rent Successfully")}
                        405 -> Log.d("VOLLEY RESPONSE", "Scooter is not renting")
                    }
                },
                errorListener,
                api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun getRents(context: Context, adapter: HistoryRecyclerViewAdapter, rentDao: RentDao, userUuid: Long) {
            val url = "https://patinfly.com/endpoints/rent"
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url,
                { response ->
                    val rents = VolleyRentParser.parseFromJson(response, userUuid)
                    Log.d("VOLLEY RESPONSE", "Rents: ${rents.rents}")
                    adapter.setItems(rents, 1)
                    adapter.notifyDataSetChanged()
                    DevUtils.insertRents(rentDao, rents)
                },
                errorListener,
                api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        fun getScooters(context: Context, adapter: ScooterRecyclerViewAdapter, scooterDao: ScooterDao) {
            val url = "https://patinfly.com/endpoints/scooter"
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url,
                { response ->
                    val scooters = ScooterParser.parseFromJson(response)
                    Log.d("VOLLEY RESPONSE COMPANION", "Scooters: ${scooters.scooters}")
                    adapter.setItems(scooters, 1)
                    adapter.notifyDataSetChanged()
                    DevUtils.insertScooters(scooterDao, scooters)
                },
                errorListener,
                api_key
            )
            RequestQueueSingl.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }

        private fun getCode(jsonString: String): Int {
            val parser = JsonParser()
            val obj = parser.parse(jsonString).asJsonObject
            return obj.get("code").asInt
        }
    }

}