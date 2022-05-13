package com.example.patinfly.model

import com.google.gson.Gson

class RentParser {
    companion object{
        fun parseFromJson( json: String):Rents{
            val gson = Gson()
            var historyElements: Rents
            json.let {
                historyElements = gson.fromJson<Rents>(json, Rents::class.java)
            }
            return historyElements
        }
    }
}