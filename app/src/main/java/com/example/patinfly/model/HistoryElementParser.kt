package com.example.patinfly.model

import android.util.Log
import com.google.gson.Gson

class HistoryElementParser {
    companion object{
        fun parseFromJson( json: String):HistoryElements{
            val gson: Gson = Gson()
            Log.e("HistoryRecycler111", json)
            var historyElements: HistoryElements
            json.let {
                historyElements = gson.fromJson<HistoryElements>(json, HistoryElements::class.java)
                Log.e("HistoryRecycler222", historyElements.historyElements.toString())
            }?:run{
                historyElements = HistoryElements()
            }
            Log.e("HistoryRecyclerFFF", historyElements.historyElements.toString())
            return historyElements
        }
    }
}