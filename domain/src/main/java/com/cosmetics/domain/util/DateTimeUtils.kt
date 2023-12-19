package com.cosmetics.domain.util

import com.google.gson.internal.`$Gson$Preconditions`.checkArgument
import java.text.SimpleDateFormat
import java.util.*

const val TIME_FORMAT = "hh:mm a"
const val MONT_YEAR_FORMAT = "MMM yyyy"
const val DAY_FORMAT = "d"

fun getDateFromTimeStamp(s: String?): String {
    return try {
        val daySdf = SimpleDateFormat(DAY_FORMAT)
        val monthYearSdf = SimpleDateFormat(MONT_YEAR_FORMAT)
        val netDate = Date(s!!.toLong() * 1000)
        val dayData = daySdf.format(netDate)
        val day = dayData + getDayOfMonthSuffix(dayData.toInt())
        val monthYear = monthYearSdf.format(netDate)
        "$day $monthYear"
    } catch (e: Exception) {
        ""
    }
}

fun getDayOfMonthSuffix(n: Int): String? {
    checkArgument(n in 1..31)
    return if (n in 11..13) {
        "th"
    } else when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

fun getTimeFromTimeStamp(s: String?): String? {
    return try {
        val sdf = SimpleDateFormat(TIME_FORMAT)
        val netDate = Date(s!!.toLong() * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        ""
    }
}