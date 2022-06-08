package com.example.patinfly.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.databinding.FragmentHistoryBinding
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.volley.GetRentsListener
import com.example.patinfly.volley.HttpRequests

class HistoryFragment : Fragment() {
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.historyRefresh.isRefreshing = true
        //Initialize Adapter
        val adapter = HistoryRecyclerViewAdapter()

        //Rent Dao
        val database = AppDatabase.getInstance(context!!)
        val rentDao = database.rentDao()

        //Get rents from HttpRequest, when done adapter.notifyDataSetChanged will be called
        //Afterwards it stores scooters in Room Database
        val sharedPref = DevUtils.getEncryptedPrefs(context!!)
        val userUuid = sharedPref.getLong("STORED_LOGIN_UUID", 0)
        val successListener = GetRentsListener(adapter, rentDao, userUuid, null)
        HttpRequests.getRents(context!!, successListener)

        // RecyclerView
        historyRecyclerView = binding.historyRecyclerView
        historyRecyclerView.setHasFixedSize(true)                                                   // Increase performance when the size is static
        historyRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)       // Our RecyclerView is using the linear layout manager
        historyRecyclerView.adapter = adapter                                                       // Set adapter

        val rents = DevUtils.getRents(rentDao, userUuid)
        adapter.setItems(rents,0)
        adapter.notifyDataSetChanged()
        binding.historyRefresh.isRefreshing = false

        binding.historyRefresh.setOnRefreshListener {
            binding.historyRefresh.isRefreshing = true
            val refreshListener = GetRentsListener(adapter, rentDao, userUuid, binding.historyRefresh)
            HttpRequests.getRents(context!!, refreshListener)
        }
    }
}