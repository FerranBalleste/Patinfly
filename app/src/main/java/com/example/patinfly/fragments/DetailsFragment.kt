package com.example.patinfly.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.patinfly.R
import com.example.patinfly.databinding.FragmentDetailsBinding
import com.example.patinfly.volley.GetScooterListener
import com.example.patinfly.volley.HttpRequests
import com.example.patinfly.volley.StartRentListener

class DetailsFragment: Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var scooterUuid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_details, container, false)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        val args: DetailsFragmentArgs by navArgs()
        binding.detailsName.text = args.name
        binding.detailsLat.text = args.latitude
        binding.detailsLon.text = args.longitude
        binding.detailsDate.text = args.date
        binding.detailsDistance.text = args.distance
        binding.detailsImage.setImageResource(when (args.battery.toDouble().toInt()*5/100){
            0 -> R.drawable.outline_battery_0_bar_24
            1 -> R.drawable.outline_battery_1_bar_24
            2 -> R.drawable.outline_battery_2_bar_24
            3 -> R.drawable.outline_battery_3_bar_24
            4 -> R.drawable.outline_battery_5_bar_24
            5 -> R.drawable.outline_battery_full_24
            6 -> R.drawable.outline_battery_0_bar_24
            else -> R.drawable.outline_battery_0_bar_24
        })
        scooterUuid = args.uuid
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val successListener =  GetScooterListener(binding)
        HttpRequests.getScooterState(context!!, successListener, scooterUuid)

        val navController = this.findNavController()

        binding.detailsButtonReturn.setOnClickListener{
            navController.popBackStack()
        }

        binding.detailsButtonRent.setOnClickListener{
            val action = DetailsFragmentDirections.actionDetailsFragmentToRentFragment(scooterUuid)
            val listener = StartRentListener(navController, action, binding.detailsRentstatus)
            HttpRequests.rentStart(context!!, listener, scooterUuid)
            //navController.navigate(action)
        }
    }
}