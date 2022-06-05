package com.example.patinfly.volley

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.RentParser
import com.example.patinfly.model.ScooterParser
import com.example.patinfly.model.Scooters
import com.example.patinfly.persitence.RentDao
import com.example.patinfly.persitence.ScooterDao
import com.example.patinfly.repositories.ScooterRepository


class RequestQueueSingl constructor(context: Context){

    companion object{
        @Volatile
        private var INSTANCE: RequestQueueSingl? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RequestQueueSingl(context).also {
                    INSTANCE = it
                }
            }
    }

    //lazy: función que durante la primera invocación ejecuta el lambda que se le haya pasado
    // y en posteriores invocaciones retornará el valor computado inicialmente.
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}