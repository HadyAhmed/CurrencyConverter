package com.hadi.currency_converter.ui.views.historical

import com.hadi.model.HistoricalData

data class HistoricalDataViewState(
    val loading: Boolean = false,
    val data: HistoricalData = HistoricalData.IDLE,
    val baseAmount: Float = 1f
) {
    companion object {
        val IDLE = HistoricalDataViewState()
    }
}
