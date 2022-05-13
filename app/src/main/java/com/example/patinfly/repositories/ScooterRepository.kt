package com.example.patinfly.repositories

import android.content.Context
import android.util.Log
import com.example.patinfly.base.AppConfig
import com.example.patinfly.model.ScooterParser
import com.example.patinfly.model.Scooters
import com.example.patinfly.persitence.Scooter


class ScooterRepository {
    companion object {

        fun activeScooterList(context: Context, resource: String): List<Scooter> {
            val scooters: Scooters = ScooterRepository.activeScooters(context, resource)
            return scooters.scooters
        }

        fun activeScooters(context: Context, resource: String): Scooters {
            val scooters: Scooters
            val jsonResource: String? = AssetsProvider.getJsonDataFromRawAsset(context, resource)
            jsonResource.let {
                scooters = ScooterParser.parseFromJson(jsonResource!!)
            }
            Log.i("SCOOTER FILE", scooters.scooters.toString())
            return scooters
        }

        fun activeScooters(context: Context): Scooters {
            val resource: String = AppConfig.DEFAULT_SCOOTER_RAW_JSON_FILE
            return ScooterRepository.activeScooters(context, resource)
        }
        /*
        fun activeScooters(): Scooters {
            val scooters: Scooters = Scooters()
            val uuidList: Array<String> =AppConfig.DEFAULT_SCOOTERS_ID_ARRAY
            var scooter: Scooter
            uuidList.forEach {
                scooter = Scooter(uuid = it, name = it)
                scooters.scooters.add(scooter)
            }

            return scooters
        }
         */
    }
}