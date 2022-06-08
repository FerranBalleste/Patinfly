package com.example.patinfly.volley

import android.annotation.SuppressLint
import com.android.volley.Response
import com.example.patinfly.databinding.FragmentDetailsBinding
import com.example.patinfly.model.ScooterParser

class GetScooterListener(
    val binding: FragmentDetailsBinding
) : Response.Listener<String> {

    @SuppressLint("SetTextI18n")
    override fun onResponse(response: String?) {
        val scooter = ScooterParser.parseFromJsonSingle(response!!)

        binding.detailsLat.text = scooter?.latitude.toString()
        binding.detailsLon.text = scooter?.longitude.toString()
        when(scooter?.onRent){
            true -> binding.detailsRentstatus.text = "Scooter is already on rent!!!"
            false -> binding.detailsRentstatus.text = "Scooter is not on rent."
        }
    }
}