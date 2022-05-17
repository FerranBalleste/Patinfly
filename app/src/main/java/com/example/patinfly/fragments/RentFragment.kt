package com.example.patinfly.fragments

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
import java.text.SimpleDateFormat
import java.util.*

class RentFragment : Fragment() {
    private lateinit var binding: FragmentRentBinding
    private lateinit var dateFormat: SimpleDateFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRentBinding.inflate(layoutInflater)
        val view = binding.root

        dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

        val args: RentFragmentArgs by navArgs()
        val startTime: String
        val startTimeMillis: Long
        Calendar.getInstance().let {
            startTime = dateFormat.format(it.time)
            startTimeMillis = it.timeInMillis
        }

        binding.rentButton.setOnClickListener{
            val database = AppDatabase.getInstance(context!!)
            val rentDao = database.rentDao()
            val rent = createRent(args.name, startTime, startTimeMillis)
            DevUtils.insertRent(rentDao, rent)

            val navController = this.findNavController()
            navController.popBackStack()
        }

        binding.rentCode.text = Random().nextInt(999999999).toString()

        return view
    }

    private fun createRent(name:String, startTime:String, startMillis:Long): Rent {
        val endTime: String
        val durationString: String
        val price: String
        Calendar.getInstance().let {
            endTime = dateFormat.format(it.time)
            val duration = (Calendar.getInstance().timeInMillis - startMillis)/60000
            durationString = duration.toString() + " min"
            price = (duration.toDouble()/10 + 2).toString() + "€"
        }
        val distance = Random().nextInt(50).toString() + "km"

        val sharedPref = DevUtils.getEncryptedPrefs(context!!)
        val uuid = sharedPref.getLong("STORED_LOGIN_UUID", 0)
        return Rent(name+startTime, name, startTime, endTime, durationString, price, distance, uuid)
    }

}