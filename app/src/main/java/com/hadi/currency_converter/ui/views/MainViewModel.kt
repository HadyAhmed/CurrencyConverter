package com.hadi.currency_converter.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadi.currency_converter.utils.currencyFormatter
import com.hadi.model.DataResult
import com.hadi.model.Rate
import com.hadi.usecase.FetchLatestRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.floor

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchLatestRateUseCase: FetchLatestRateUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(MainViewState.IDLE)
    val viewState = _viewState.asStateFlow()

    private val _showSnackBarMsg = MutableSharedFlow<String?>()
    val showSnackBarMsg = _showSnackBarMsg.asSharedFlow()

    init {
        fetchInitialRates()
    }

    private fun fetchInitialRates(base: String? = null) {
        viewModelScope.launch {
            _viewState.update { it.copy(loading = true) }
            val result = fetchLatestRateUseCase(base = base)
            _viewState.update { it.copy(loading = false) }
            when (result) {
                is DataResult.Failure -> _showSnackBarMsg.emit(result.throwable.message)
                is DataResult.Success -> {
                    _viewState.update {
                        it.copy(
                            fromCurrencies = result.data.rateApiModel,
                            toCurrencies = result.data.rateApiModel
                        )
                    }
                }
            }
        }
    }

    fun selectFromValue(from: Rate) {
        _viewState.update { it.copy(fromValue = from) }
        fetchInitialRates(from.label)
    }

    fun selectToValue(to: Rate) {
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
                toInputValue = floor(
                    it.fromInputValue.toFloatOrNull() ?: 1f
                ).times(it.toValue.value).currencyFormatter()
            )
        }
    }

    private fun updateFromState() {
        _viewState.update {
            it.copy(
                fromInputValue = floor(
                    it.toInputValue.toFloatOrNull() ?: 1f
                ).div(it.fromValue.value).currencyFormatter()
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
}