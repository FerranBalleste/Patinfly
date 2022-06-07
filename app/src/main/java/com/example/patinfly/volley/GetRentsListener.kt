package com.example.patinfly.volley

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.android.volley.Response
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.VolleyRentParser
import com.example.patinfly.persitence.RentDao
import com.google.gson.JsonParser

class GetRentsListener(
    val adapter: HistoryRecyclerViewAdapter, val rentDao: RentDao, val userUuid: Long
) : Response.Listener<String> {

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponse(response: String?) {
        val rents = VolleyRentParser.parseFromJson(response!!, userUuid)
        Log.d("VOLLEY RESPONSE", "Rents: ${rents.rents}")
        adapter.setItems(rents, 1)
        adapter.notifyDataSetChanged()
        DevUtils.insertRents(rentDao, rents)
    }
}