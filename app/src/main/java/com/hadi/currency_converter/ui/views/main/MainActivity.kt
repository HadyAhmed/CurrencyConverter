package com.hadi.currency_converter.ui.views.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.hadi.currency_converter.R
import com.hadi.currency_converter.ui.compose.CurrencyConverterToolbar
import com.hadi.currency_converter.ui.compose.CurrencyTextInput
import com.hadi.currency_converter.ui.compose.DropDownMenu
import com.hadi.currency_converter.ui.theme.CurrencyConverterTheme
import com.hadi.currency_converter.ui.views.historical.HistoricalDataActivity
import com.hadi.currency_converter.ui.views.historical.HistoricalDataViewModel
import com.hadi.currency_converter.utils.convertToNumber
import com.hadi.currency_converter.utils.currentDate
import com.hadi.currency_converter.utils.currentDateTime
import com.hadi.currency_converter.utils.hideKeyboard
import com.hadi.model.HistoricalDataRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Scaffold(topBar = { CurrencyConverterToolbar("Currency Converter") }) {
                    MainScreenContent(
                        onDetailsClick = { amount, base ->
                            startActivity(
                                Intent(this, HistoricalDataActivity::class.java)
                                    .putExtra(
                                        HistoricalDataViewModel.HISTORICAL_DATA_REQUEST,
                                        HistoricalDataRequest(
                                            base = base,
                                            startDate = currentDateTime(date = currentDate(-3)),
                                            endDate = currentDateTime(),
                                        )
                                    ).putExtra(
                                        HistoricalDataViewModel.HISTORICAL_DATA_AMOUNT,
                                        amount
                                    )
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.showToastMessage.onEach {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }.launchIn(lifecycleScope)
    }

    @Composable
    fun MainScreenContent(
        modifier: Modifier = Modifier,
        onDetailsClick: (amount: Float, base: String) -> Unit
    ) {
        val currencies = viewModel.viewState.collectAsState()
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = currencies.value.loading
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            SwitchCurrency(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)
                    .padding(horizontal = 32.dp)
            )

            ConvertCurrencyValues(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 32.dp)
            )

            AnimatedVisibility(visible = currencies.value.fromValue.label != "From" && currencies.value.toValue.label != "To") {
                OutlinedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        onDetailsClick(
                            currencies.value.fromInputValue.convertToNumber(),
                            currencies.value.fromValue.label
                        )
                    }
                ) {
                    Text("Details")
                }
            }
        }
    }

    @Composable
    fun SwitchCurrency(modifier: Modifier = Modifier) {
        val currencies = viewModel.viewState.collectAsState()
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            DropDownMenu(
                modifier = Modifier.weight(1f),
                selectedItem = currencies.value.fromValue,
                items = currencies.value.fromCurrencies,
                onItemSelected = {
                    if (it.label == currencies.value.fromValue.label) return@DropDownMenu
                    viewModel.selectFromValue(it)
                },
            )

            IconButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { viewModel.switchSelection() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_swap_horiz_24),
                    contentDescription = "Switch"
                )
            }
            DropDownMenu(
                modifier = Modifier.weight(1f),
                selectedItem = currencies.value.toValue,
                items = currencies.value.toCurrencies,
                onItemSelected = {
                    viewModel.selectToValue(it)
                },
            )
        }
    }

    @Composable
    fun ConvertCurrencyValues(modifier: Modifier = Modifier) {
        val currencies = viewModel.viewState.collectAsState()
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CurrencyTextInput(
                modifier = Modifier.weight(1f),
                value = currencies.value.fromInputValue,
                onValueChange = viewModel::changeFromInputValue,
                onDoneClick = { hideKeyboard() }
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            CurrencyTextInput(
                modifier = Modifier.weight(1f),
                value = currencies.value.toInputValue,
                onValueChange = viewModel::changeToInputValue,
                onDoneClick = { hideKeyboard() }
            )
        }
    }

    @Preview(showBackground = true, name = "Main Screen")
    @Composable
    fun DefaultPreview() {
        CurrencyConverterTheme {
            MainScreenContent(modifier = Modifier.fillMaxSize(), onDetailsClick = { _, _ -> })
        }
    }

    @Preview(showBackground = true, name = "Top bar")
    @Composable
    fun ToolbarPreview() {
        CurrencyConverterTheme {
            CurrencyConverterToolbar("Currency Converter")
        }
    }

    @Preview(showBackground = true, name = "Switch Currencies")
    @Composable
    fun SwitchCurrencyPreview() {
        CurrencyConverterTheme {
            SwitchCurrency(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }

    @Preview(showBackground = true, name = "Switch Currencies Value")
    @Composable
    fun SwitchCurrencyValuePreview() {
        CurrencyConverterTheme {
            ConvertCurrencyValues(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }

}
