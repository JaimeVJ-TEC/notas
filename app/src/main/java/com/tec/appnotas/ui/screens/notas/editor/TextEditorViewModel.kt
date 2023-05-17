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

    private val _content = MutableStateFlow(RichTextValue.get())
    val content : StateFlow<RichTextValue> = _content

    private val _alignment = MutableStateFlow(Alignment.LEFT)
    val alignment: MutableStateFlow<Alignment> = _alignment

    private val _size = MutableStateFlow(Style.TextSize.DEFAULT_VALUE)
    val size: StateFlow<Float> = _size

    private val _styles = MutableStateFlow<MutableList<Styles>>(mutableListOf())
    val styles: StateFlow<MutableList<Styles>> = _styles

    private var selectedRange = Pair(0,0)

    init{
        _title.value = nota.titulo
        if (!nota.richTextValue?.value?.text.isNullOrEmpty()) {
            _content.value = nota.richTextValue!!
        }
    }

    fun updateTitle(title: String){
        _title.value = title
    }

    fun onContentChanged(content: RichTextValue){
        _content.value = content
    }

    fun selectContent(start : Int, end: Int){
        selectedRange = Pair(start,end)
    }

    fun changeAlignment(index: Int){
        _alignment.value = intToAlignment(index)
        _content.value = _content.value.clearStyles(Style.AlignRight,Style.AlignCenter, Style.AlignLeft)
        _content.value =  _content.value.insertStyle(style = alignmentToRichTextAlignment(_alignment.value))
    }

    fun onUpdateStyles(index: Int) {
        val style = intToStyle(index)
        if(_styles.value.contains(style)){
            _styles.value.remove(style)
            _content.value = _content.value.clearStyles(styleToRichTextStyle(style))
        }
        else{
            _styles.value.add(style)
            _content.value =  _content.value.insertStyle(style = styleToRichTextStyle(style))
        }
    }

    fun onSizeChange(it: Boolean) {
        _size.value += if(it) 1 else -1
        _content.value = _content.value.insertStyle(Style.TextSize(_size.value))
    }

    private fun intToAlignment(index: Int): Alignment{
        return when(index){
            0 ->Alignment.LEFT
            1 ->Alignment.CENTER
            2 ->Alignment.RIGHT
            else -> throw IllegalArgumentException("Invalid integer value")
        }
    }

    private fun styleToRichTextStyle(style: Styles): Style{
        return when(style){
            Styles.BOLD -> Style.Bold
            Styles.ITALICS -> Style.Italic
            Styles.UNDERLINE -> Style.Underline
            Styles.STRIKE -> Style.Strikethrough
        }
    }

    private fun alignmentToRichTextAlignment(alignment: Alignment): Style{
        return when(alignment){
            Alignment.LEFT -> Style.AlignLeft
            Alignment.CENTER -> Style.AlignCenter
            Alignment.RIGHT-> Style.AlignRight
        }
    }

    private fun intToStyle(index: Int): Styles{
        return when (index) {
            0 -> Styles.BOLD
            1 -> Styles.UNDERLINE
            2 -> Styles.STRIKE
            3 -> Styles.ITALICS
            else -> throw IllegalArgumentException("Invalid integer value")
        }
    }

}

enum class Alignment {
    LEFT,
    CENTER,
    RIGHT
}

enum class Styles{
    BOLD,
    ITALICS,
    UNDERLINE,
    STRIKE
}