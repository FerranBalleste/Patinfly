package com.example.patinfly.volley

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.android.volley.Response
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonParser

class StopRentListener(val context: Context, val navController: NavController?) : Response.Listener<String> {

    override fun onResponse(response: String?) {
        when (getCode(response!!)) {
            200 -> navController?.let{
                    Toast.makeText(context, "Rent Stopped Successfully", Toast.LENGTH_LONG).show()
                    it.popBackStack()
                }
            404 -> Toast.makeText(context, "ERROR, Scooter does not exist", Toast.LENGTH_LONG).show()
            405 -> Toast.makeText(context, "ERROR, Scooter already on rent!!!", Toast.LENGTH_LONG).show()
            409 -> Toast.makeText(context, "ERROR, Scooter not available, wrong user", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCode(jsonString: String): Int {
        val parser = JsonParser()
        val obj = parser.parse(jsonString).asJsonObject
        return obj.get("code").asInt
    }
}