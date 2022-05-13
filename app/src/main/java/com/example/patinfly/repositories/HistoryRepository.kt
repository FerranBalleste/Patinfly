package com.example.patinfly.repositories

import android.content.Context
import com.example.patinfly.base.AppConfig
import com.example.patinfly.model.*


class HistoryRepository {
    companion object {

        /*fun activeHistoryList(context: Context, resource: String): List<HistoryElement> {
            val historyElements: Rents = activeHistory(context, resource)
            return historyElements.historyElements
        }*/

        fun activeHistory(context: Context, resource: String): Rents {
            val historyElements: Rents
            val jsonResource: String? = AssetsProvider.getJsonDataFromRawAsset(context, resource)
            jsonResource.let {
                historyElements = RentParser.parseFromJson(jsonResource!!)
            }
            return historyElements
        }

        fun activeHistory(context: Context): Rents {
            val resource: String = AppConfig.DEFAULT_HISTORY_RAW_JSON_FILE
            return activeHistory(context, resource)
        }

        /*
        fun activeHistory(): HistoryElements {
            val historyElements: HistoryElements = HistoryElements()
            val uuidList: Array<String> =AppConfig.DEFAULT_HISTORY_ID_ARRAY
            var historyElement: HistoryElement
            uuidList.forEach {
                historyElement = HistoryElement(uuid = it, name = it, startTime = it, endTime = it, distance = it, price = it, duration = it)
                historyElements.historyElements.add(historyElement)
            }

            return historyElements
        }
        */
    }
}