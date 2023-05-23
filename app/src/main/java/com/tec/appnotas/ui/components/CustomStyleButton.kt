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
    SelectItem("italic","", Icons.Default.FormatItalic,"Italic Text"),
    SelectItem("underline","", Icons.Default.FormatUnderlined,"Underline Text"),
    SelectItem("strikethrough","", Icons.Default.FormatStrikethrough,"Strikethrough Text"),
    SelectItem("alignLeft","", Icons.Default.FormatAlignLeft,"Align Left"),
    SelectItem("alignCenter","", Icons.Default.FormatAlignCenter,"Align Center"),
    SelectItem("alignRight","", Icons.Default.FormatAlignRight,"Align Right"),
    SelectItem("setHeader","", Icons.Default.FormatSize,"Header"),
    SelectItem("setText","", Icons.Default.TextFields,"Body"),
    SelectItem("insertImage","",Icons.Default.NoteAdd,"InsertFile"),
    SelectItem("insertPhoto","",Icons.Default.PhotoCamera,"TakePhoto")
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