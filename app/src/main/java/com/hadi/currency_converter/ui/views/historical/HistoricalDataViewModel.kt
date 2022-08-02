package com.hadi.currency_converter.ui.views.historical

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadi.model.DataResult
import com.hadi.model.HistoricalDataRequest
import com.hadi.usecase.FetchHistoricalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricalDataViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val historicalDataUseCase: FetchHistoricalDataUseCase
) : ViewModel() {
    val historicalDataRequestArg =
        savedStateHandle.get<HistoricalDataRequest>(HISTORICAL_DATA_REQUEST)
    private val baseAmountArg = savedStateHandle.get<Float>(HISTORICAL_DATA_AMOUNT)

    private val _viewState = MutableStateFlow<HistoricalDataViewState>(HistoricalDataViewState.IDLE)
    val viewState = _viewState.asStateFlow()

    private val _showToastMessage = MutableSharedFlow<String?>()
    val showToastMessage = _showToastMessage.asSharedFlow()

    init {
        historicalDataRequestArg?.let { fetchHistoricalData(it) }
        _viewState.update { it.copy(baseAmount = baseAmountArg ?: 1f) }
    }

    private fun fetchHistoricalData(request: HistoricalDataRequest) {
        viewModelScope.launch {
            _viewState.update { it.copy(loading = true) }
            val result = historicalDataUseCase(request)

            _viewState.update { it.copy(loading = false) }
            when (result) {
                is DataResult.Failure -> _showToastMessage.emit(result.throwable.message)
                is DataResult.Success -> _viewState.update { it.copy(data = result.data) }
            }
        }
    }

    companion object {
        const val HISTORICAL_DATA_REQUEST = "historical_data"
        const val HISTORICAL_DATA_AMOUNT = "base_amount"
    }
}