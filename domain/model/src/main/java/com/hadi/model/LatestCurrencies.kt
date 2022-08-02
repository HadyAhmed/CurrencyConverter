package com.hadi.model

data class LatestCurrencies(
    val base: String,
    val rateApiModel: List<Rate>,
)
