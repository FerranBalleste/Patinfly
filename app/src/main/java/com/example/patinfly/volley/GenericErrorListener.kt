package com.example.patinfly.volley

import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyError

class GenericErrorListener: Response.ErrorListener {
    override fun onErrorResponse(error: VolleyError?) {
        Log.e("VOLLEY GENERIC ERROR LISTENER","That didn't work!")
    }
}