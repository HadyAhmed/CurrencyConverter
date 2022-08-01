package com.hadi.network.model

import com.google.gson.annotations.SerializedName

data class LatestCurrenciesApiModel(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rateApiModel: Any,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Int
)
