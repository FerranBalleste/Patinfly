package com.example.patinfly.model

import android.util.Log
import com.google.gson.Gson

class HistoryElementParser {
    companion object{
        fun parseFromJson( json: String):HistoryElements{
            val gson: Gson = Gson()

            var historyElements: HistoryElements
            json.let {
                historyElements = gson.fromJson<HistoryElements>(json, HistoryElements::class.java)
            }?:run{
                historyElements = HistoryElements()
            }
            return historyElements
        }
    }
}