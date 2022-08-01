package com.hadi.currency_converter.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
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
import com.hadi.currency_converter.ui.compose.CurrencyTextInput
import com.hadi.currency_converter.ui.compose.DropDownMenu
import com.hadi.currency_converter.ui.theme.CurrencyConverterTheme
import com.hadi.currency_converter.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Scaffold(topBar = { CurrencyConverterToolbar() }) {
                    MainScreenContent(
                        onDetailsClick = {

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
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }.launchIn(lifecycleScope)
    }

    @Composable
    fun MainScreenContent(modifier: Modifier = Modifier, onDetailsClick: () -> Unit) {
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
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = onDetailsClick
            ) {
                Text("Details")
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

    @Composable
    fun CurrencyConverterToolbar() {
        TopAppBar(elevation = 8.dp) {
            Text("Currency Converter", style = MaterialTheme.typography.h6)
        }
    }


    @Preview(showBackground = true, name = "Main Screen")
    @Composable
    fun DefaultPreview() {
        CurrencyConverterTheme {
            MainScreenContent(modifier = Modifier.fillMaxSize(), onDetailsClick = {})
        }
    }

    @Preview(showBackground = true, name = "Top bar")
    @Composable
    fun ToolbarPreview() {
        CurrencyConverterTheme {
            CurrencyConverterToolbar()
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
