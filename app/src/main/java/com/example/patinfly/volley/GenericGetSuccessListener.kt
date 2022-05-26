package com.example.patinfly.volley

import android.util.Log
import com.android.volley.Response

class GenericGetSuccessListener: Response.Listener<String> {
    override fun onResponse(response: String?) {
        Log.d("VOLLEY GENERIC GET SUCCESS","Response is: ${response?.substring(0, 500)}")
    }
}