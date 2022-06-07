package com.example.patinfly.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.R
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.volley.GetRentsListener
import com.example.patinfly.volley.HttpRequests

class HistoryFragment : Fragment() {
    private lateinit var historyRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize Adapter
        val adapter = HistoryRecyclerViewAdapter()

        //Rent Dao
        val database = AppDatabase.getInstance(context!!)
        val rentDao = database.rentDao()

        //Get rents from HttpRequest, when done adapter.notifyDataSetChanged will be called
        //Afterwards it stores scooters in Room Database
        val sharedPref = DevUtils.getEncryptedPrefs(context!!)
        val userUuid = sharedPref.getLong("STORED_LOGIN_UUID", 0)
        HttpRequests.getRents(context!!, GetRentsListener(adapter,rentDao, userUuid))

        // RecyclerView
        historyRecyclerView = view.findViewById(R.id.history_recycler_view)
        historyRecyclerView.setHasFixedSize(true)                                                   // Increase performance when the size is static
        historyRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)       // Our RecyclerView is using the linear layout manager
        historyRecyclerView.adapter = adapter                                                       // Set adapter

        val rents = DevUtils.getRents(rentDao, userUuid)
        adapter.setItems(rents,0)
        adapter.notifyDataSetChanged()
    }
}