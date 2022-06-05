package com.example.patinfly.volley

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.RentParser
import com.example.patinfly.model.ScooterParser
import com.example.patinfly.model.Scooters
import com.example.patinfly.model.VolleyRentParser
import com.example.patinfly.persitence.RentDao
import com.example.patinfly.persitence.ScooterDao
import com.example.patinfly.repositories.ScooterRepository


class HttpRequests {

    companion object{

        //rent start jsonObjectRequest
        /*
        {
            "code": 405,
            "msg": "Scooter is not vacant",
            "rent": {},
            "timestamp": "2022-05-26T12:59:53.012",
            "vesion": 1.0
        }
         */
        //rent stop jsonObjectRequest
        /*
        {
    "code": 200,
    "msg": "OK",
    "rent": {
        "uuid": "12ec14d6-dc00-11ec-8a4b-ecf4bbcc40f8",
        "date_start": "2022-05-25T07:55:41.722Z",
        "date_stop": "2022-05-26T13:00:36.921"
    },
    "timestamp": "2022-05-26T13:00:36.942",
    "vesion": 1.0
}
         */

        private const val api_key = "0NoXsbyfOczMKkS1qrZgxBEiwlIFvT87DpYPm6ed"

        fun getRents(context: Context, adapter: HistoryRecyclerViewAdapter, rentDao: RentDao, userUuid: Long){
            // Instantiate the RequestQueue. The queue is unique for all the requests
            val queue = RequestQueueSingl.getInstance(context).requestQueue
            val url = "https://patinfly.com/endpoints/rent"
            val genericErrorListener= GenericErrorListener()

            //Request with anonymous success json listener and the generic error listener
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->
                    val rents = VolleyRentParser.parseFromJson(response, userUuid)
                    Log.d("VOLLEY RESPONSE","Rents: ${rents.rents}")
                    adapter.setItems(rents, 1)
                    adapter.notifyDataSetChanged()
                    DevUtils.insertRents(rentDao,rents)
                },
                genericErrorListener,
                api_key
            )
            // Add the json the request to the RequestQueue
            queue.add(jsonObjectRequest)
        }

        fun getScooters(context: Context, adapter: ScooterRecyclerViewAdapter, scooterDao: ScooterDao){
            // Instantiate the RequestQueue. The queue is unique for all the requests
            val queue = RequestQueueSingl.getInstance(context).requestQueue
            val url = "https://patinfly.com/endpoints/scooter"
            val genericErrorListener= GenericErrorListener()

            //Request with anonymous success json listener and the generic error listener
            val jsonObjectRequest = CustomStringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->
                    val scooters = ScooterParser.parseFromJson(response)
                    Log.d("VOLLEY RESPONSE COMPANION","Scooters: ${scooters.scooters}")
                    adapter.setItems(scooters, 1)
                    adapter.notifyDataSetChanged()
                    DevUtils.insertScooters(scooterDao, scooters)
                },
                genericErrorListener,
                api_key
            )
            // Add the json the request to the RequestQueue
            queue.add(jsonObjectRequest)
        }
    }

}