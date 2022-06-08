package com.example.patinfly.volley

import android.annotation.SuppressLint
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Response
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.VolleyRentParser
import com.example.patinfly.persitence.RentDao

class GetRentsListener(
    val adapter: HistoryRecyclerViewAdapter, val rentDao: RentDao, val userUuid: Long, val refresh:SwipeRefreshLayout?
) : Response.Listener<String> {

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponse(response: String?) {
        val rents = VolleyRentParser.parseFromJson(response!!, userUuid)
        adapter.setItems(rents, 1)
        adapter.notifyDataSetChanged()
        refresh?.isRefreshing = false
        DevUtils.insertRents(rentDao, rents)
    }
}