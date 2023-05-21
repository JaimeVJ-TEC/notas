package com.tec.appnotas.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tec.appnotas.data.constants.disabledGrey

val Styles = listOf(
    SelectItem("bold","", Icons.Default.FormatBold,"Bold Text"),
    SelectItem("italic","", Icons.Default.FormatItalic,"Bold Text"),
    SelectItem("underline","", Icons.Default.FormatUnderlined,"Bold Text"),
    SelectItem("strikethrough","", Icons.Default.FormatStrikethrough,"Bold Text"),
    SelectItem("alignLeft","", Icons.Default.FormatAlignLeft,"Bold Text"),
    SelectItem("alignCenter","", Icons.Default.FormatAlignCenter,"Bold Text"),
    SelectItem("alignRight","", Icons.Default.FormatAlignRight,"Bold Text"),
    SelectItem("setHeader","", Icons.Default.FormatSize,"Bold Text"),
    SelectItem("setText","", Icons.Default.TextFields,"Bold Text")
)

@Composable
fun StyleButtonRow(items: List<SelectItem>, selectedUpdate: (String) -> Unit) {
    Surface(
        color = disabledGrey
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .wrapContentHeight(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            items.forEachIndexed { index, selectItem ->
                StyleButton(
                    item = selectItem
                ) {
                    selectedUpdate(selectItem.id)
                }
            }
        }
    }
}

@Composable
fun StyleButton(
    item: SelectItem,
    onClick: () -> Unit
) {

    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .background(MaterialTheme.colors.primarySurface)
    ) {
        Icon(
            imageVector = item.icon!!,
            contentDescription = item.description
        )
    }
}