package com.example.patinfly.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.databinding.FragmentHomeBinding
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.volley.GetScootersListener
import com.example.patinfly.volley.HttpRequests

class HomeFragment : Fragment() {

    private lateinit var scooterRecyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize Adapter
        val navController = this.findNavController()
        val adapter = ScooterRecyclerViewAdapter(navController)

        //Get scooter Dao
        val database = AppDatabase.getInstance(context!!)
        val scooterDao = database.scooterDao()

        //Get scooters from HttpRequest, when done adapter.notifyDataSetChanged will be called
        //Afterwards it stores scooters in Room Database
        val successListener = GetScootersListener(adapter, scooterDao, null)
        HttpRequests.getScooters(context!!, successListener)

        //Recycler
        scooterRecyclerView = binding.homeRecyclerView
        scooterRecyclerView.setHasFixedSize(true)                                                   // Increase performance when the size is static
        scooterRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)       // Our RecyclerView is using the linear layout manager
        scooterRecyclerView.adapter = adapter                                                       // Set adapter

        //Load from Room (usually loads faster than Volley)
        val scooters = DevUtils.getScooters(scooterDao)
        adapter.setItems(scooters, 0)
        adapter.notifyDataSetChanged()

        binding.homeRefresh.setOnRefreshListener {
            binding.homeRefresh.isRefreshing = true
            val startListener = GetScootersListener(adapter, scooterDao, binding.homeRefresh)
            HttpRequests.getScooters(context!!, startListener)
        }

    }

}