package com.hadi.model

data class HistoricalData(
    val base: String,
    val startDate: String,
    val endDate: String,
    val rates: List<HistoricalRate>,
) {
    companion object {
        val IDLE = HistoricalData(base = "", startDate = "", endDate = "", rates = emptyList())
    }
}
