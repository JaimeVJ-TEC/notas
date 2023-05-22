package com.tec.appnotas.ui.screens.notas.editor

import RichEditorCompose
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.ui.global.GlobalProvider

@Composable
fun NotaScreen(navController: NavHostController, globalProvider: GlobalProvider, nota: Nota){
    val editorVM = TextEditorViewModel(nota)
    Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .navigationBarsPadding().imePadding()
            .fillMaxHeight()) {
        editor(editorViewModel = editorVM)
    }

}

@Composable
fun editor(editorViewModel: TextEditorViewModel){
    val title = editorViewModel.title.collectAsState().value
    RichEditorCompose(
        title,
        onContentUpdate = {editorViewModel.onContentChanged(it)},
        onTitleUpdate = {editorViewModel.updateTitle(it)},
        LocalContext.current
    )

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