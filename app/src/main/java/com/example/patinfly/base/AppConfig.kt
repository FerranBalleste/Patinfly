package com.example.patinfly.base


class AppConfig {
    companion object{
        val DEFAULT_SCOOTER_RAW_JSON_FILE: String = "scooters"
        val DEFAULT_HISTORY_RAW_JSON_FILE: String = "history"

        val DEFAULT_HISTORY_ID_ARRAY : Array<String> = arrayOf<String>("PFTGN-00001", "PFTGN-00002", "PFTGN-00003", "PFTGN-00004", "PFTGN-00005", "PFTGN-00006")
        val DEFAULT_SCOOTERS_ID_ARRAY : Array<String> = arrayOf<String>("HTGN-00001", "HTGN-00002", "HTGN-00003", "HTGN-00004", "HTGN-00005", "HTGN-00006")
    }
}