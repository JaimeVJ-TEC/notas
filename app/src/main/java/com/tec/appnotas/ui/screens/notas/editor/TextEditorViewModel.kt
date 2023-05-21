package com.tec.appnotas.ui.screens.notas.editor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import android.util.Log
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.pointlessapps.rt_editor.model.RichTextValue
import com.pointlessapps.rt_editor.model.Style
import com.pointlessapps.rt_editor.ui.RichTextStyle
import com.tec.appnotas.ui.screens.notas.Nota

class TextEditorViewModel(private val nota: Nota) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title : StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content : StateFlow<String> = _content

    private var selectedRange = Pair(0,0)

    init{
        _title.value = nota.titulo
        _content.value = nota.contenido
    }

    fun updateTitle(title: String){
        _title.value = title
    }

    fun onContentChanged(content: String){
        _content.value = content
        Log.d("CONTENT",_content.value)
    }
}