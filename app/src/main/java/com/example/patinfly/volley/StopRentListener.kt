package com.example.patinfly.volley

import android.util.Log
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.android.volley.Response
import com.google.gson.JsonParser

class StopRentListener(
    val navController: NavController?
) : Response.Listener<String> {

    override fun onResponse(response: String?) {
        when (getCode(response!!)) {
            200 -> {navController?.popBackStack(); Log.d("VOLLEY RESPONSE", "Stopped rent Successfully")}
            405 -> Log.d("VOLLEY RESPONSE", "Scooter is not renting")
        }
    }

    private fun getCode(jsonString: String): Int {
        val parser = JsonParser()
        val obj = parser.parse(jsonString).asJsonObject
        return obj.get("code").asInt
    }
}