package com.example.patinfly.volley

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError

class GenericErrorListener(val context: Context): Response.ErrorListener {
    override fun onErrorResponse(error: VolleyError?) {
        Toast.makeText(context, "ERROR, NETWORK MIGHT BE DOWN", Toast.LENGTH_LONG).show()
        Log.e("VOLLEY GENERIC ERROR LISTENER","That didn't work!")
    }
}