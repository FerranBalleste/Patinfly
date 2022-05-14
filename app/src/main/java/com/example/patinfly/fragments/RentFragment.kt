package com.example.patinfly.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.patinfly.R
import com.example.patinfly.databinding.FragmentDetailsBinding
import com.example.patinfly.databinding.FragmentRentBinding

class RentFragment : Fragment() {
    private lateinit var binding: FragmentRentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRentBinding.inflate(layoutInflater)
        val view = binding.root

        val args: RentFragmentArgs by navArgs()
        binding.rentButton.setOnClickListener{
            val navController = this.findNavController()
            navController.popBackStack()
        }

        return view
    }

}