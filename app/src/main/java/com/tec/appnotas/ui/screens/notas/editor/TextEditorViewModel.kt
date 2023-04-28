package com.tec.appnotas.ui.screens.notas.editor

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextEditorViewModel : ViewModel() {

    private val _content = MutableStateFlow<AnnotatedString>(AnnotatedString(""))
    val content : StateFlow<AnnotatedString> = _content

}