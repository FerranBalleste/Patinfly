package com.example.patinfly.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.patinfly.R

class RentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rent, container, false)

        view.findViewById<Button>(R.id.rent_button).setOnClickListener{
            parentFragmentManager.popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }

}