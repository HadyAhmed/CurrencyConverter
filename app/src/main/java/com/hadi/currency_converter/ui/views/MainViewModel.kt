package com.hadi.currency_converter.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadi.usecase.FetchLatestRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject
import kotlin.math.floor

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchLatestRateUseCase: FetchLatestRateUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.IDLE)
    val viewState = _viewState.asStateFlow()

    init {
        fetchInitialRates()
    }

    private fun fetchInitialRates() {
        viewModelScope.launch {
            val result = fetchLatestRateUseCase()

        }
    }

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

    companion object {
        private const val TAG = "MainViewModel"
    }
}