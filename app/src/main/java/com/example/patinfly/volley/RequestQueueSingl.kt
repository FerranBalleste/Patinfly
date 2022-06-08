package com.example.patinfly.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

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

    //lazy: only calculates the first time
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}