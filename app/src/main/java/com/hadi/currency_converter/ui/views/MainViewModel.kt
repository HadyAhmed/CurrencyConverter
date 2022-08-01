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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchLatestRateUseCase: FetchLatestRateUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(MainViewState.IDLE)
    val viewState = _viewState.asStateFlow()

    private val _showToastMessage = MutableSharedFlow<String?>()
    val showToastMessage = _showToastMessage.asSharedFlow()

    init {
        fetchInitialRates()
    }

    /**
     * this will update the currency rate data to change the UI values from and to
     * @param base is the country currency code passed to the API, by default no base to fetch all country currency at the beginning
     */
    private fun fetchInitialRates(base: String? = null) {
        viewModelScope.launch {
            _viewState.update { it.copy(loading = true) }
            val result = fetchLatestRateUseCase(base = base)
            _viewState.update { it.copy(loading = false) }
            when (result) {
                is DataResult.Failure -> _showToastMessage.emit(result.throwable.message)
                is DataResult.Success -> {
                    _viewState.update {
                        it.copy(
                            fromCurrencies = result.data.rateApiModel,
                            toCurrencies = result.data.rateApiModel
                        )
                    }
                    selectToValue(_viewState.value.toCurrencies.find { it.label == _viewState.value.toValue.label })
                }
            }
        }
    }


    fun selectFromValue(from: Rate) {
        _viewState.update { it.copy(fromValue = from) }
        fetchInitialRates(from.label)
        updateFromState()
    }

    fun selectToValue(to: Rate?) {
        to?.let {
            _viewState.update { it.copy(toValue = to) }
            updateToState()
        }
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
                toInputValue = (
                        it.fromInputValue.trim().replace(",", "").replace("،", "").toFloatOrNull()
                            ?: 1f
                        ).times(it.toValue.value).currencyFormatter()
            )
        }
    }

    private fun updateFromState() {
        _viewState.update {
            it.copy(
                fromInputValue = (it.toInputValue.trim().replace(",", "").replace("،", "")
                    .toFloatOrNull()
                    ?: 1f).div(it.toValue.value)
                    .currencyFormatter()
            )
        }
    }

    fun switchSelection() {
        _viewState.update {
            it.copy(
                toValue = it.fromValue,
                fromValue = it.toValue,
                toInputValue = it.fromInputValue,
                fromInputValue = it.toInputValue,
            )
        }
        fetchInitialRates(_viewState.value.fromValue.label)
    }
}