package com.hadi.currency_converter.utils

import java.text.DecimalFormat
import java.text.NumberFormat

fun Number.currencyFormatter(): String {
    val formatter: NumberFormat = DecimalFormat("###,###.##")
    return formatter.format(this)
}

fun String.convertToNumber(): Float {
    return trim().replace(",", "").replace("،", "").toFloatOrNull()
        ?: 1f
}