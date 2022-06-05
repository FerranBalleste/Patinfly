package com.example.patinfly.model

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson

class VolleyRentParser {
    companion object{
        fun parseFromJson(json: String, userUuid: Long):Rents{
            val gson = Gson()
            var volleyRents: VolleyRents
            json.let {
                volleyRents = gson.fromJson<VolleyRents>(json, VolleyRents::class.java)
            }
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                volleyRents.formatRents(userUuid)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        }
    }
}