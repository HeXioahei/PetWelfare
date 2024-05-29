package com.example.petwelfare.logic.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

object TimeBuilder {
    fun getNowTime(): String {

        var month2 = "0"
        var day2 = "0"
        var hour2 = "0"
        var minute2 = "0"
        var second2 = "0"

        val year = LocalDateTime.now().year
        val month = LocalDateTime.now().monthValue.apply { if (this < 9 ) month2 = "0$this" }
        val day = LocalDateTime.now().dayOfMonth.apply { if (this < 9 ) day2 = "0$this" }
        val hour = LocalDateTime.now().hour.apply { if (this < 9 ) hour2 = "0$this" }
        val minute = LocalDateTime.now().minute.apply { if (this < 9 ) minute2 = "0$this" }
        val second = LocalDateTime.now().second.apply { if (this < 9 ) second2 = "0$this" }
        val time = "$year-$month2-$day2    $hour2: $minute2: $second2"
        return time
    }

}