package com.hadi.currency_converter.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hadi.model.Rate

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    selectedItem: Rate,
    items: List<Rate>,
    onItemSelected: (Rate) -> Unit,
) {
    Spinner<Rate>(
        modifier = modifier,
        items = items,
        selectedItem = selectedItem,
        onItemSelected = onItemSelected,
        selectedItemFactory = { selectedModifier, item ->
            Row(
                modifier = selectedModifier
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(item.label, style = TextStyle(fontWeight = FontWeight.Medium))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "drop down arrow"
                )
            }
        },
        dropdownItemFactory = { value, _ ->
            Text(
                text = value.label,
                style = MaterialTheme.typography.body1
            )
        },
    )
}

@Composable
fun <T> Spinner(
    modifier: Modifier = Modifier,
    dropDownModifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    selectedItemFactory: @Composable (Modifier, T) -> Unit,
    dropdownItemFactory: @Composable (T, Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(modifier = modifier.clip(RoundedCornerShape(8.dp))) {
        selectedItemFactory(
            Modifier.clickable { expanded = true },
            selectedItem
        )
        DropdownMenu(
            modifier = dropDownModifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, element ->
                DropdownMenuItem(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    onClick = {
                        onItemSelected(items[index])
                        expanded = false
                    }) {
                    dropdownItemFactory(element, index)
                }
            }
        }
    }
}
