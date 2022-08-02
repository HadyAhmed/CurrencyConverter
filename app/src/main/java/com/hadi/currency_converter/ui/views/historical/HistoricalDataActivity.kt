package com.hadi.currency_converter.ui.views.historical

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.hadi.currency_converter.ui.compose.CurrencyConverterToolbar
import com.hadi.currency_converter.ui.theme.CurrencyConverterTheme
import com.hadi.currency_converter.utils.currencyFormatter
import com.hadi.model.HistoricalRate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalFoundationApi
@AndroidEntryPoint
class HistoricalDataActivity : ComponentActivity() {

    private val viewModel by viewModels<HistoricalDataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Scaffold(
                    topBar = {
                        CurrencyConverterToolbar(
                            title = "Historical Data For ${viewModel.historicalDataRequestArg?.base ?: "..."}",
                            navIcon = Icons.Default.ArrowBack,
                            onNavigationClick = { onBackPressed() })
                    }) {
                    val viewState = viewModel.viewState.collectAsState()

                    Box(modifier = Modifier.fillMaxSize()) {
                        if (viewState.value.loading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        } else {
                            HistoricalDataList(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun HistoricalDataList(modifier: Modifier = Modifier) {
        val viewState = viewModel.viewState.collectAsState()
        Row(modifier = modifier) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.secondary)
                            .padding(8.dp),
                        text = "Historical List",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                items(viewState.value.data.rates) { rate ->
                    HistoricalItem(rate)
                }
            }
            // this will prevent to throw exception when size of the list is less 10
            if (viewState.value.data.rates.isNotEmpty() && viewState.value.data.rates.size >= 11)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    stickyHeader {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colors.secondary)
                                .padding(8.dp),
                            text = "Other Currencies",
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    items(viewState.value.data.rates.shuffled().subList(0, 10)) { rate ->
                        HistoricalConvertedItem(
                            baseAmount = viewState.value.baseAmount,
                            rate = rate
                        )
                    }
                }
        }
    }

    @Composable
    private fun HistoricalItem(rate: HistoricalRate) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = rate.label, style = MaterialTheme.typography.subtitle2)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Start Rate: ${rate.startRate}",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "End Rate: ${rate.endRate}",
                    style = MaterialTheme.typography.caption
                )
                Text(text = "Change: ${rate.change}", style = MaterialTheme.typography.caption)
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Change PCT : ${rate.changePct}",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }

    @Composable
    private fun HistoricalConvertedItem(baseAmount: Float, rate: HistoricalRate) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = rate.label, style = MaterialTheme.typography.subtitle2)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Start Converted Value:\n${
                        rate.startRate.toFloat().times(baseAmount).currencyFormatter()
                    }",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "End Converted Value:\n${
                        rate.endRate.toFloat().times(baseAmount).currencyFormatter()
                    }",
                    style = MaterialTheme.typography.caption
                )
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
}