package com.example.patinfly.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.R
import com.example.patinfly.adapters.HistoryRecyclerViewAdapter
import com.example.patinfly.base.AppConfig
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.Rents
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.repositories.HistoryRepository

class HistoryFragment : Fragment() {
    private lateinit var historyRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        // historyElements
        //val historyElements: Rents = HistoryRepository.activeHistory(requireActivity(), AppConfig.DEFAULT_HISTORY_RAW_JSON_FILE)
        val database = AppDatabase.getInstance(context!!)
        val rentDao = database.rentDao()
        val sharedPref = DevUtils.getEncryptedPrefs(context!!)
        val email = sharedPref.getString(getString(R.string.preference_key_login_email), "")
        Log.i("HISTORY FRAGMENT", email!!)
        val rents = DevUtils.getRents(rentDao, email!!)

        // RecyclerView
        historyRecyclerView = view.findViewById(R.id.history_recycler_view)
        historyRecyclerView.setHasFixedSize(true)                                                   // Increase performance when the size is static
        historyRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)       // Our RecyclerView is using the linear layout manager
        historyRecyclerView.adapter = HistoryRecyclerViewAdapter(rents)                             // Set adapter

        return view
    }
}