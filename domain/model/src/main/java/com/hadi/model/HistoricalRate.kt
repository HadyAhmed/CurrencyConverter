package com.hadi.model

data class HistoricalRate(
    val label: String,
    val change: Double,
    val changePct: Double,
    val endRate: Double,
    val startRate: Double
)
