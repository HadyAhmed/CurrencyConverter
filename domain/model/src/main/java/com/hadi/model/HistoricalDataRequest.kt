package com.hadi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoricalDataRequest(
    val base: String,
    val startDate: String,
    val endDate: String,
) : Parcelable
