package com.tec.appnotas.ui.screens.notas.editor

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pointlessapps.rt_editor.model.RichTextValue
import com.pointlessapps.rt_editor.model.Style
import com.pointlessapps.rt_editor.ui.RichTextEditor
import com.pointlessapps.rt_editor.ui.defaultRichTextFieldStyle
import com.tec.appnotas.ui.components.AlignmentButtons
import com.tec.appnotas.ui.components.SizeButtons
import com.tec.appnotas.ui.components.StyleButtons
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.screens.notas.Nota

@Composable
fun NotaScreen(navController: NavHostController, globalProvider: GlobalProvider, nota: Nota){
    val editorVM = TextEditorViewModel(nota)
    editor(editorViewModel = editorVM)

}


//@Preview
//@Composable
//fun test(){
//    editor(editorViewModel = TextEditorViewModel())
//}

@Composable
fun editor(editorViewModel: TextEditorViewModel){
    val title = editorViewModel.title.collectAsState().value
    val alignment = editorViewModel.alignment.collectAsState().value
    val styles = editorViewModel.styles.collectAsState().value
    val content = editorViewModel.content.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            titleField(title = title) { editorViewModel.updateTitle(it) }
            Spacer(modifier = Modifier.height(5.dp))
            styleSelectionRow(
                selectedStyles = content.currentStyles,
                onSelectedUpdate = {
                    editorViewModel.onUpdateStyles(it)
                },
                onSizeChange = {
                    editorViewModel.onSizeChange(it)
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            textEditor(content = content) {
                editorViewModel.onContentChanged(it)
            }
        }

    }

}

@Composable
fun titleField(title: String, onTitleChange: (String) -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
    ){
        TextField(
            value = title,
            onValueChange = {onTitleChange(it)},
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
            maxLines = 1
        )
    }
}

@Composable
fun textEditor(content: RichTextValue,onContentChange: (RichTextValue) -> Unit){
    RichTextEditor(
        value = content,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(16.dp),
        onValueChange = {onContentChange(it)},
        textFieldStyle = defaultRichTextFieldStyle().copy(
            textColor = MaterialTheme.colors.onSecondary
        )
    )
}

@Composable
fun styleSelectionRow(selectedStyles: Set<Style>, onSelectedUpdate: (Int) -> Unit, onSizeChange: (Boolean) -> Unit){
    val scrollState = rememberScrollState()
    Row(modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(scrollState)
        .wrapContentHeight(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        StyleButtons(
            selectedIndexes = selectedStyles.map{
                when (it) {
                    Style.Bold -> 0
                    Style.Underline-> 1
                    Style.Strikethrough -> 2
                    Style.Italic -> 3
                    else -> -1
                }
            }
        ) {
            onSelectedUpdate(it)
        }
        Spacer(modifier = Modifier.width(5.dp))

//        SizeButtons(onSizeUp = {onSizeChange(it)})

//        AlignmentButtons(
//            selectedIndex = if(selectedStyles.contains(Style.AlignCenter)) 1
//            else if(selectedStyles.contains(Style.AlignRight)) 2 else 0
//        )
//        {
//            onSelectedChange(it)
//        }
    }
}

//        styleSelectionRow(
//            selectedStyles = styles,
//            selectedAlignment = alignment,
//            onSelectedChange = {
//                editorViewModel.changeAlignment(it)
//            },
//            onSelectedUpdate = {
//                editorViewModel.onUpdateStyles(it)
//            }
//        )

//@Composable
//fun styleRow(content: RichTextValue, onContentChange: () -> Unit){
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.BottomCenter
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .background(Color.DarkGray)
//                .horizontalScroll(rememberScrollState()),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            EditorAction(icon = Icons.Default.FormatBold, active = content.currentStyles.contains(Style.Bold)) {
////                editorViewModel.onUpdateStyles(Styles.BOLD)
//                onContentChange()
//            }
//        }
//    }
//}

//@Composable
//private fun EditorAction(
//    icon: ImageVector,
//    active: Boolean,
//    onClick: () -> Unit,
//) {
//    IconButton(onClick = onClick) {
//        Icon(
//            imageVector = icon,
//            modifier = Modifier.size(24.dp),
//            tint = if (active) Color.White else Color.Black,
//            contentDescription = null
//        )
//    }
//}