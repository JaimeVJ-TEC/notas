package com.tec.appnotas.ui.screens.notas.editor

import RichEditorCompose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tec.appnotas.data.constants.LibretaTopColor
import com.tec.appnotas.ui.global.GlobalProvider

@Composable
fun NotaScreen(navController: NavHostController, globalProvider: GlobalProvider, id: Int){
    val editorVM : TextEditorViewModel = hiltViewModel()
    val initialized = editorVM.initialized.collectAsState().value
    editorVM.getNota(id)

    if(initialized) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .navigationBarsPadding()
                .imePadding()
                .fillMaxHeight()
        ) {
            editor(editorViewModel = editorVM)
        }
    }
}

@Composable
fun editor(editorViewModel: TextEditorViewModel){
    val nota = editorViewModel.nota.collectAsState().value
    RichEditorCompose(
        nota.title,
        onContentUpdate = {editorViewModel.onContentChanged(it)},
        onTitleUpdate = {editorViewModel.updateTitle(it)},
        LocalContext.current,
        nota.content,
    )
}


@Composable
fun titleField(title: String, onTitleChange: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colors.background)
            .testTag("editor_title")
    ) {
        TextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .focusRequester(focusRequester),
            singleLine = true
        )
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
    }
}


