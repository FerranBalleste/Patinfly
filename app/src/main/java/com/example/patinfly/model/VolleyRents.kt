package com.example.patinfly.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.Rent
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class VolleyRents{
    var rents: LinkedList<VolleyRent> = LinkedList()

    fun formatRents(userUuid: Long): Rents{
        val result = Rents()
        for(vRent in rents){
            val stop = vRent.date_stop
            if(stop != null){
                val duration = calculateDuration(vRent.date_start, stop)
                val price = (duration * 2 + 0.5).toString() + "€"
                val rent = Rent(vRent.uuid, vRent.scooter.name, vRent.date_start, stop, duration.toString() + "min", price, userUuid)
                result.rents.add(rent)
            }
        }
        return result
    }

    private fun calculateDuration(start: String, stop:String):Long{
        val instant1 = Instant.parse(start)
        val instant2 = Instant.parse(stop)
        return instant1.until(instant2, ChronoUnit.MINUTES)
    }
}