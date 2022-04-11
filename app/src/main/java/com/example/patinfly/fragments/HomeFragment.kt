package com.example.patinfly.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.R
import com.example.patinfly.adapters.ScooterRecyclerViewAdapter
import com.example.patinfly.base.AppConfig
import com.example.patinfly.model.Scooters
import com.example.patinfly.repositories.ScooterRepository

class HomeFragment : Fragment() {

    private lateinit var scooterRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // scooterElements
        val scooters: Scooters = ScooterRepository.activeScooters(requireActivity(), AppConfig.DEFAULT_SCOOTER_RAW_JSON_FILE)
        scooterRecyclerView = view.findViewById(R.id.home_recycler_view)
        scooterRecyclerView.setHasFixedSize(true)                                                   // Increase performance when the size is static
        scooterRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)       // Our RecyclerView is using the linear layout manager
        scooterRecyclerView.adapter = ScooterRecyclerViewAdapter(scooters)                          // Set adapter

        /*
        //Click on card
        view.findViewById<MaterialCardView>(R.id.home_card1).setOnClickListener {
            val table = view.findViewById<TableLayout>(R.id.home_hidable_table)
            if(table.visibility == View.VISIBLE){
                table.visibility = View.GONE
            }else{
                table.visibility = View.VISIBLE
            }
        }

        view.findViewById<Button>(R.id.home_button1).setOnClickListener {
            parentFragmentManager.commit {
                replace<RentFragment>(R.id.nav_host_fragment_content_drawer)
                setReorderingAllowed(true)
                addToBackStack("home")
            }
        }
        */
        return view
    }

}