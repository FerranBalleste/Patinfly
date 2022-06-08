package com.example.patinfly.volley

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.android.volley.Response
import com.google.gson.JsonParser

class StartRentListener(
    val navController: NavController,
    val action: NavDirections,
    val textView: TextView
) : Response.Listener<String> {

    @SuppressLint("SetTextI18n")
    override fun onResponse(response: String?) {
        when (getCode(response!!)) {
            200 -> navController.navigate(action)
            404 -> textView.text = "Scooter does not exist."
            405 -> textView.text = "Scooter is already on rent!!!"
            409 -> textView.text = "Scooter not available, wrong user."
        }
    }

    private fun getCode(jsonString: String): Int {
        val parser = JsonParser()
        val obj = parser.parse(jsonString).asJsonObject
        return obj.get("code").asInt
    }
}