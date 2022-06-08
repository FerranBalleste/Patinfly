package com.example.patinfly.volley

import android.annotation.SuppressLint
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Response
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.ScooterParser
import com.example.patinfly.persitence.ScooterDao

class GetScootersListener(
    val adapter: ScooterRecyclerViewAdapter, val scooterDao: ScooterDao, val refresh:SwipeRefreshLayout?
) : Response.Listener<String> {

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponse(response: String?) {
        val scooters = ScooterParser.parseFromJson(response!!)
        adapter.setItems(scooters, 1)
        adapter.notifyDataSetChanged()
        refresh?.isRefreshing = false
        DevUtils.insertScooters(scooterDao, scooters)
    }
}