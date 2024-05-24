package com.example.petwelfare.logic.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

object TimeBuilder {
    fun getNowTime(): String {
        val year = LocalDateTime.now().year
        val month = LocalDateTime.now().monthValue
        val day = LocalDateTime.now().dayOfMonth
        val hour = LocalDateTime.now().hour.apply { if (this < 9 ) "0$this" }
        val minute = LocalDateTime.now().minute.apply { if (this < 9 ) "0$this" }
        val second = LocalDateTime.now().second.apply { if (this < 9 ) "0$this" }
        val time = "$year-$month-$day    $hour: $minute: $second"
        return time
    }

}