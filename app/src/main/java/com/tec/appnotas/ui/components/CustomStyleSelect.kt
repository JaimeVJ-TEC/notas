package com.tec.appnotas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tec.appnotas.data.constants.disabledGrey

@Composable
fun CustomStyleSelectionText(items: List<SelectItem>, selectedIndex: Int ,selectionChange : () -> Unit){
    Surface(
        color = disabledGrey,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row() {

            items.forEachIndexed { index, selectItem ->
                val round = index == 0 || index == items.size - 1
                val left = index == 0
                CustomSelectTextButton(
                    round = round,
                    left = left,
                    item = selectItem,
                    index = index,
                    selectedIndex = selectedIndex
                )
                {
                    selectionChange()
                }
            }
        }
    }
}

@Composable
fun CustomStyleSelectionIcon(items: List<SelectItem>, selectedIndex: Int ,selectionChange : (Int) -> Unit){
    Surface(
        color = disabledGrey,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row() {

            items.forEachIndexed { index, selectItem ->
                val round = index == 0 || index == items.size - 1
                val left = index == 0
                CustomSelectIconButton(
                    round = round,
                    left = left,
                    item = selectItem,
                    index = index,
                    selectedIndex = selectedIndex
                )
                {
                    selectionChange(index)
//                    selectedIndex = index
                }
            }
        }
    }
}

@Composable
fun CustomSelectIconButton(round: Boolean, left: Boolean, item: SelectItem, index: Int, selectedIndex: Int, onClick : () -> Unit){
    val shape = if(selectedIndex == index){
        RoundedCornerShape(16.dp)
        } else if (round) {
            if (left) {
                RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
            } else {
                RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            }
        } else {
            RoundedCornerShape(0.dp)
        }

    Box(
        modifier = Modifier
            .background(if (selectedIndex == index) MaterialTheme.colors.onPrimary else disabledGrey, shape)
            .clip(shape)
    ) {
        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                imageVector = item.icon!!,
                contentDescription = item.description
            )
        }
    }
}

@Composable
fun CustomSelectTextButton(round: Boolean, left: Boolean, item: SelectItem, index: Int, selectedIndex: Int, onClick : () -> Unit){
    val shape = if(selectedIndex == index){
        RoundedCornerShape(16.dp)
    } else if (round) {
        if (left) {
            RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
        } else {
            RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
        }
    } else {
        RoundedCornerShape(0.dp)
    }

    Box(
        modifier = Modifier
            .background(if (selectedIndex == index) MaterialTheme.colors.onPrimary else disabledGrey, shape)
            .clip(shape)
    ) {
        IconButton(
            onClick = { onClick() },
        ) {
            Text(
                text = item.text,
                modifier = Modifier.background(Color.Transparent)
            )
        }
    }
}

data class SelectItem(
    val id: String,
    val text: String = "",
    val icon: ImageVector? = null,
    val description: String = ""
)
