package com.example.petwelfare.logic.model

import java.time.LocalDateTime

object TimeBuilder {
    fun getNowTime(): String {

        var year2: String
        var month2: String
        var day2: String
        var hour2: String
        var minute2: String
        var second2: String

        LocalDateTime.now().year.apply { year2 = this.toString() }
        LocalDateTime.now().monthValue.apply { month2 = if (this < 9) "0$this" else this.toString()}
        LocalDateTime.now().dayOfMonth.apply { day2 = if (this < 9) "0$this}" else this.toString() }
        LocalDateTime.now().hour.apply { hour2 = if (this < 9) "0$this" else this.toString() }
        LocalDateTime.now().minute.apply { minute2 = if (this < 9) "0$this" else this.toString() }
        LocalDateTime.now().second.apply { second2 = if (this < 9) "0$this" else this.toString() }
        return "$year2-$month2-$day2    $hour2: $minute2: $second2"
    }

}