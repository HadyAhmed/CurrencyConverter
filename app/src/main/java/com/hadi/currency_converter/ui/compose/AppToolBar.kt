package com.hadi.currency_converter.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyConverterToolbar(
    title: String,
    navIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {}
) {
    TopAppBar(elevation = 8.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            navIcon?.let {
                IconButton(onClick = onNavigationClick) {
                    Icon(imageVector = it, contentDescription = "navigation icon")
                }
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = title,
                style = MaterialTheme.typography.h6
            )
        }
    }
}