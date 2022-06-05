package com.example.patinfly.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.patinfly.databinding.FragmentRentBinding
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.persitence.Rent
import com.example.patinfly.volley.HttpRequests
import java.text.SimpleDateFormat
import java.util.*

class RentFragment : Fragment() {
    private lateinit var binding: FragmentRentBinding
    private var savedRent: Boolean = false
    private lateinit var scooterUuid:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentBinding.inflate(layoutInflater)
        val view = binding.root

        val args: RentFragmentArgs by navArgs()
        scooterUuid = args.name

        binding.rentButton.setOnClickListener{
            val navController = this.findNavController()
            HttpRequests.rentStop(context!!, navController, scooterUuid)
            savedRent = true
        }

        binding.rentCode.text = Random().nextInt(999999999).toString()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!savedRent)
            HttpRequests.rentStop(context!!, null, scooterUuid)
    }

}