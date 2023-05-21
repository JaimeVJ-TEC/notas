import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import jp.wasabeef.richeditor.RichEditor
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.tec.appnotas.ui.components.StyleButtonRow
import com.tec.appnotas.ui.components.Styles
import com.tec.appnotas.ui.screens.notas.editor.titleField

class RichEditorComposeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val editor: RichEditor

    init {
        editor = RichEditor(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        addView(editor)
    }
}

private fun onStyleButtonClick(richEditorComposeView: RichEditorComposeView, style: String) {
    Log.d("asd",style)
    when (style) {
        "bold" -> {
            Log.d("Style","BOLD")
            richEditorComposeView.editor.setBold()}
        "italic" -> richEditorComposeView.editor.setItalic()
        "underline" -> richEditorComposeView.editor.setUnderline()
        "strikethrough" -> richEditorComposeView.editor.setStrikeThrough()
        "alignLeft" -> richEditorComposeView.editor.setAlignLeft()
        "alignCenter" -> richEditorComposeView.editor.setAlignCenter()
        "alignRight" -> richEditorComposeView.editor.setAlignRight()
        "setHeader" -> richEditorComposeView.editor.setFontSize(5)
        "setText" -> richEditorComposeView.editor.setFontSize(3)
        "insertImage" -> {
            // Insert an image using a sample URL
            richEditorComposeView.editor.insertImage("https://via.placeholder.com/150", "Sample Image")
        }
    }
}

//@Preview
//@Composable
//fun text(){
//    var title by remember{ mutableStateOf("")}
//    RichEditorCompose(title,onContentUpdate = {},{title = it})
//}


@Composable
fun RichEditorCompose(title: String,onContentUpdate: (String) -> Unit,onTitleUpdate: (String) -> Unit, context: Context) {
    val context = LocalContext.current
    val richEditorComposeView = remember {
        RichEditorComposeView(context).apply {
            // Customize the RichEditor settings here, e.g.:
            editor.setEditorHeight(200)
            editor.setEditorFontSize(14)
            editor.setOnTextChangeListener {
                onContentUpdate(it)
            }
        }
    }
    val scrollState = rememberScrollState()

    Column {
        titleField(title = title) { onTitleUpdate(it) }
        Spacer(modifier = Modifier.height(5.dp))
        AndroidView(
            factory = { richEditorComposeView },
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .weight(1f)
        )
        StyleButtonRow(items = Styles, selectedUpdate = { onStyleButtonClick(richEditorComposeView, it) })
    }
}