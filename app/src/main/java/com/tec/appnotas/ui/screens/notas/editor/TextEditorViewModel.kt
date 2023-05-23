package com.tec.appnotas.ui.screens.notas.editor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import android.util.Log
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.pointlessapps.rt_editor.model.RichTextValue
import com.pointlessapps.rt_editor.model.Style
import com.pointlessapps.rt_editor.ui.RichTextStyle
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.NotaRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextEditorViewModel @Inject constructor(
    private val notaRepositoryImp: NotaRepository
) : ViewModel() {

    private val _nota = MutableStateFlow(Nota(-1))
    val nota: StateFlow<Nota> = _nota

    private var selectedRange = Pair(0,0)

    private val _initialized = MutableStateFlow(false)
    val initialized : StateFlow<Boolean> = _initialized

    fun getNota(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            if (!_initialized.value) {
                _nota.value = notaRepositoryImp.getNotaById(id)
                _initialized.value = true
            }
        }
    }

    fun updateTitle(title: String){
        _nota.value = _nota.value.copy(title = title)
        Log.d("TITLE",_nota.value.title)
        updateNota(_nota.value)
    }

    fun onContentChanged(content: String){
        _nota.value = _nota.value.copy(content = content)
        Log.d("CONTENT",_nota.value.content)
        updateNota(_nota.value)
    }

    private fun updateNota(nota: Nota){
        viewModelScope.launch {
            notaRepositoryImp.updateLocalNota(nota)
        }
    }


}