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
import com.example.patinfly.model.HistoryElement
import com.example.patinfly.model.HistoryElements
import com.example.patinfly.repositories.HistoryRepository

class HistoryFragment : Fragment() {

    private lateinit var historyRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecyclerView = view.findViewById(R.id.history_recycler_view)
        /*
        val historyElements: HistoryElements = HistoryRepository.activeHistory(activity!!, AppConfig.DEFAULT_HISTORY_RAW_JSON_FILE)
        Log.e("HistoryRecycler", historyElements.historyElements.toString())
         */
        val historyElements = HistoryElements()
        historyElements.historyElements.add(HistoryElement("banana", "UUID", "25-9-2020 19:00", "25-9-2020 20:00", "6", "9", "10"))

        // Increase performance when the size is static
        historyRecyclerView.setHasFixedSize(true)

        // Our RecyclerView is using the linear layout manager
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        historyRecyclerView.layoutManager = layoutManager

        val adapter = HistoryRecyclerViewAdapter(historyElements)
        historyRecyclerView.adapter = adapter

        return view
    }
}