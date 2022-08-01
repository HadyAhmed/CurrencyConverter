package com.hadi.currency_converter.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.floor

class MainViewModel : ViewModel() {
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.IDLE)
    val viewState = _viewState.asStateFlow()

    fun selectFromValue(from: String) {
        _viewState.update { it.copy(fromValue = from) }
    }

    fun selectToValue(to: String) {
        _viewState.update { it.copy(toValue = to) }
    }

    fun changeFromInputValue(from: String) {
        _viewState.update { it.copy(fromInputValue = from) }
        updateToState()
    }

    fun changeToInputValue(to: String) {
        _viewState.update { it.copy(toInputValue = to) }
        updateFromState()
    }

    private fun updateToState() {
        _viewState.update {
            it.copy(
                toInputValue = floor(it.fromInputValue.toFloatOrNull() ?: 1f).times(18.98f)
                    .currencyFormatter()
            )
        }
    }

    private fun updateFromState() {
        _viewState.update {
            it.copy(
                fromInputValue = floor(it.toInputValue.toFloatOrNull() ?: 1f).div(18.98f)
                    .currencyFormatter()
            )
        }
    }

    fun switchSelection() {
        _viewState.update {
            it.copy(
                toValue = it.fromValue,
                toInputValue = it.fromInputValue,
                fromInputValue = it.toInputValue,
                fromValue = it.toValue,
            )
        }
    }

    private fun Number.currencyFormatter(): String {
        val formatter: NumberFormat = DecimalFormat("##,###.##")
        return formatter.format(this)
    }
}