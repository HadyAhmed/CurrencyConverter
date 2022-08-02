package com.hadi.network.model.response

import com.google.gson.annotations.SerializedName

data class HistoricalDataApiModel(
    @SerializedName("base")
    val base: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("fluctuation")
    val fluctuation: Boolean,
    @SerializedName("rates")
    val rates: Any,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("success")
    val success: Boolean
)