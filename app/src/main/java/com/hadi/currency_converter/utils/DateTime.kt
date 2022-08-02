package com.hadi.currency_converter.utils

import java.text.SimpleDateFormat
import java.util.*

fun currentDateTime(
    pattern: String? = null, date: Date = Date()
): String {
    val sdf = SimpleDateFormat(pattern ?: "yyyy-MM-dd", Locale.getDefault())
    return sdf.format(date)
}

/**
 * this will return date in specific day
 * @param days increasing days means return date after period of days, decreasing days means return date before period of days
 */
fun currentDate(days: Int = 0): Date {
    return Calendar.getInstance().apply {
        set(
            Calendar.DAY_OF_MONTH,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + days
        )
    }.time
}