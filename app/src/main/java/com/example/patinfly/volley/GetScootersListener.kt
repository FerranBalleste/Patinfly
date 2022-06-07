package com.example.patinfly.volley

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.android.volley.Response
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.ScooterParser
import com.example.patinfly.model.VolleyRentParser
import com.example.patinfly.persitence.RentDao
import com.example.patinfly.persitence.ScooterDao
import com.google.gson.JsonParser

class GetScootersListener(
    val adapter: ScooterRecyclerViewAdapter, val scooterDao: ScooterDao
) : Response.Listener<String> {

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponse(response: String?) {
        val scooters = ScooterParser.parseFromJson(response!!)
        Log.d("VOLLEY RESPONSE COMPANION", "Scooters: ${scooters.scooters}")
        adapter.setItems(scooters, 1)
        adapter.notifyDataSetChanged()
        DevUtils.insertScooters(scooterDao, scooters)
    }
}