package com.example.patinfly.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.patinfly.R

class DetailsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        arguments?.let {
            view.findViewById<TextView>(R.id.details_name).text = it.getString("name")
            view.findViewById<TextView>(R.id.details_uuid).text = it.getString("uuid")
            view.findViewById<TextView>(R.id.details_lat).text = it.getString("lat")
            view.findViewById<TextView>(R.id.details_lon).text = it.getString("lon")
            view.findViewById<TextView>(R.id.details_date).text = it.getString("date")
            view.findViewById<TextView>(R.id.details_distance).text = it.getString("distance")
            view.findViewById<ImageView>(R.id.details_image).setImageResource(when (it.getString("battery")){
                "0" -> R.drawable.outline_battery_0_bar_24
                "1" -> R.drawable.outline_battery_1_bar_24
                "2" -> R.drawable.outline_battery_2_bar_24
                "3" -> R.drawable.outline_battery_3_bar_24
                "4" -> R.drawable.outline_battery_5_bar_24
                "5" -> R.drawable.outline_battery_full_24
                "6" -> R.drawable.outline_battery_0_bar_24
                else -> R.drawable.outline_battery_0_bar_24
            })
        }

        view.findViewById<Button>(R.id.details_button_return).setOnClickListener{
            parentFragmentManager.popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        view.findViewById<Button>(R.id.details_button_rent).setOnClickListener{
            parentFragmentManager.commit {
                replace<RentFragment>(R.id.nav_host_fragment_content_drawer)
                setReorderingAllowed(true)
                addToBackStack("home")
            }
        }

        return view
    }

}