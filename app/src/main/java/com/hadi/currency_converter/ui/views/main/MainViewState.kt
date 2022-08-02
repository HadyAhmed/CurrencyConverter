package com.hadi.currency_converter.ui.views.main

import com.hadi.model.Rate

data class MainViewState(
    val loading: Boolean = false,
    val fromCurrencies: List<Rate> = listOf(),
    val toCurrencies: List<Rate> = listOf(
    ),
    val fromValue: Rate = Rate("From", 1.0),
    val toValue: Rate = Rate("To", 1.0),
    val fromInputValue: String = "1",
    val toInputValue: String = "1",
) {
    companion object {
        val IDLE = MainViewState()
    }
}