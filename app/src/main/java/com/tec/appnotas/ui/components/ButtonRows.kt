package com.tec.appnotas.ui.components

import android.graphics.Paint.Style
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable

@Composable
fun AlignmentButtons(selectedIndex: Int, selectionChange : (Int) -> Unit){
    Row(){
        CustomStyleSelectionIcon(items = alignmentButtons,selectedIndex){
            selectionChange(it)
        }
    }
}

@Composable
fun StyleButtons(selectedIndexes: List<Int>, selectionUpdate: (Int) -> Unit){
    Row(){
        CustomStyleMultiSelect(items = styleButtons, selectedIndexes = selectedIndexes) {
            selectionUpdate(it)
        }
    }
}

@Composable
fun SizeButtons(onSizeUp: (Boolean) -> Unit){
    Row{
        CustomSelectIconButton(
            round = true,
            left = false,
            item = sizeButtons[0],
            index = 0,
            selectedIndex = 0
        )
        {
            onSizeUp(true)
        }

        CustomSelectIconButton(
            round = true,
            left = false,
            item = sizeButtons[1],
            index = 1,
            selectedIndex = 1
        )
        {
            onSizeUp(false)
        }
    }
}

val alignmentButtons = listOf(
    SelectItem("LEFT","LEFT", Icons.Default.FormatAlignLeft,"Left Alignment"),
    SelectItem("CENTER","CENTER", Icons.Default.FormatAlignCenter,"Center Alignment"),
    SelectItem("RIGHT","RIGHT", Icons.Default.FormatAlignRight,"Right Alignment")
)

val styleButtons = listOf(
    SelectItem("BOLD","BOLD", Icons.Default.FormatBold,"Bold"),
    SelectItem("UNDERLINE","UNDERLINE",Icons.Default.FormatUnderlined,"Underlined"),
    SelectItem("STRIKE","STRIKE", Icons.Default.FormatStrikethrough,"Strikethrough"),
    SelectItem("ITALIC","ITALIC",Icons.Default.FormatItalic,"Italics")
)

val sizeButtons = listOf(
    SelectItem("SizeUp","Size Up", Icons.Default.TextIncrease,"Font Size Up"),
    SelectItem("SizeDown","Size Down", Icons.Default.TextDecrease,"Font Size Down")
)