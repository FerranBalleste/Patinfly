package com.example.patinfly.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.R
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.volley.HttpRequests

class HomeFragment : Fragment() {

    private lateinit var scooterRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //scooter volley & database
        val database = AppDatabase.getInstance(context!!)
        val scooterDao = database.scooterDao()
        //Scooters gotten from HttpRequests will be stored in Room database
        HttpRequests.getScooters(context!!, scooterDao)
        //Shown scooters will be from the database, in case HttpRequest fails
        val scooters = DevUtils.getScooters(scooterDao)
        Log.d("VOLLEY RESPONSE","Scooters: ${scooters.scooters}")

        //Navigation
        //val navController = activity?.findNavController(R.id.nav_host_fragment_content_drawer)
        val navController = this.findNavController()

        //Recycler
        scooterRecyclerView = view.findViewById(R.id.home_recycler_view)
        scooterRecyclerView.setHasFixedSize(true)                                                   // Increase performance when the size is static
        scooterRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)       // Our RecyclerView is using the linear layout manager
        scooterRecyclerView.adapter = ScooterRecyclerViewAdapter(scooters, navController)   // Set adapter

        return view
    }

}