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
import com.example.patinfly.databinding.FragmentDetailsBinding

class DetailsFragment: Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_details, container, false)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        arguments?.let {
            binding.detailsName.text = it.getString("name")
            binding.detailsUuid.text = it.getString("uuid")
            binding.detailsLat.text = it.getDouble("lat").toString()
            binding.detailsLon.text = it.getDouble("lon").toString()
            binding.detailsDate.text = it.getString("date")
            binding.detailsDistance.text = it.getDouble("distance").toString()
            binding.detailsImage.setImageResource(when (it.getDouble("battery").toInt()*6/100){
                0 -> R.drawable.outline_battery_0_bar_24
                1 -> R.drawable.outline_battery_1_bar_24
                2 -> R.drawable.outline_battery_2_bar_24
                3 -> R.drawable.outline_battery_3_bar_24
                4 -> R.drawable.outline_battery_5_bar_24
                5 -> R.drawable.outline_battery_full_24
                6 -> R.drawable.outline_battery_0_bar_24
                else -> R.drawable.outline_battery_0_bar_24
            })
        }

        binding.detailsButtonReturn.setOnClickListener{
            parentFragmentManager.popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        binding.detailsButtonRent.setOnClickListener{
            parentFragmentManager.commit {
                replace<RentFragment>(R.id.nav_host_fragment_content_drawer)
                setReorderingAllowed(true)
                addToBackStack("home")
            }
        }

        return view
    }

}