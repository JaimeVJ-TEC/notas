package com.tec.appnotas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tec.appnotas.data.constants.disabledGrey

//@Preview
//@Composable
//fun test(){
//    var items = listOf(
//        SelectItem("a","asd", Icons.Default.Home,"asd"),
//        SelectItem("b","asd", Icons.Default.DateRange,"asd"),
//        SelectItem("c","asd", Icons.Default.List,"asd"),
//        SelectItem("d","asd", Icons.Default.AccountBox,"asd"),
//    )
//    CustomStyleMultiSelect(items = items)
//}

@Composable
fun CustomStyleMultiSelect(items: List<SelectItem>, selectedIndexes: List<Int>, selectedUpdate: (Int) -> Unit) {
    Surface(
        color = disabledGrey,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row {

            items.forEachIndexed { index, selectItem ->
                val round = index == 0 || index == items.size - 1
                val left = index == 0
                MultiSelectButton(
                    round = round,
                    left = left,
                    item = selectItem,
                    selected = selectedIndexes.contains(index)
                ) {
                    selectedUpdate(index)
                }
            }
        }
    }
}

@Composable
fun MultiSelectButton(
    round: Boolean,
    left: Boolean,
    item: SelectItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = if (selected) {
        RoundedCornerShape(0.dp)
    } else if (round) {
        if (left) {
            RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
        } else {
            RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
        }
    } else {
        RoundedCornerShape(0.dp)
    }

    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .clip(shape)
            .background(
                if (selected) MaterialTheme.colors.onPrimary else disabledGrey,
                shape
            )
    ) {
        Icon(
            imageVector = item.icon!!,
            contentDescription = item.description
        )
    }
}