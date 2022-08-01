package com.hadi.currency_converter.ui.screens

data class MainViewState(
    val fromCurrencies: List<String> = listOf("EGP", "USD"),
    val toCurrencies: List<String> = listOf("EGP", "USD"),
    val fromValue: String = "From",
    val toValue: String = "To",
    val fromInputValue: String = "1",
    val toInputValue: String = "1",
) {
    companion object {
        val IDLE = MainViewState()
    }
}