import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import jp.wasabeef.richeditor.RichEditor
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.tec.appnotas.BuildConfig
import com.tec.appnotas.ui.components.StyleButtonRow
import com.tec.appnotas.ui.components.Styles
import com.tec.appnotas.ui.screens.notas.editor.titleField
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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

private fun onStyleButtonClick(richEditorComposeView: RichEditorComposeView, style: String, imgRoute : String = "") {
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
            richEditorComposeView.editor.insertImage("$imgRoute", "[Image]\" style=\"max-width:70%; height:auto")
        }
        "insertPhoto" -> {
            // Insert an image using a sample URL
            Log.d("ROUTE",imgRoute)
            richEditorComposeView.editor.insertImage("$imgRoute", "[Image]\" style=\"max-width:70%; height:auto")
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
fun RichEditorCompose(title: String,onContentUpdate: (String) -> Unit,onTitleUpdate: (String) -> Unit, context: Context,text: String) {
    val context = LocalContext.current
    val theme = MaterialTheme.colors
    var initialized = false
    var uri = Uri.EMPTY
    var lastcontent = ""

    val richEditorComposeView = remember {
        RichEditorComposeView(context).apply {
            // Customize the RichEditor settings here, e.g.:
            editor.setEditorHeight(200)
            editor.setEditorFontSize(14)

            editor.setOnTextChangeListener {
                if(it.length <= 5000){
                    lastcontent = it
                }
                else{
                    editor.html = lastcontent
                }
                onContentUpdate(it)
            }
            if(!initialized) {
                editor.html = text
                initialized = true
            }
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            Log.d("URI",uri.toString())
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            onStyleButtonClick(richEditorComposeView,"insertImage",uri.toString())
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {result ->
            if(result != null && result) {
                onStyleButtonClick(richEditorComposeView, "insertImage", uri.toString())
            }
            else{
                context.contentResolver.delete(uri,null,null)
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    Column(modifier = Modifier.background(Color.White)) {
        titleField(title = title) { onTitleUpdate(it) }
        Divider()
        Spacer(modifier = Modifier.height(5.dp))
        AndroidView(
            factory = { richEditorComposeView },
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .weight(1f)
        )
        StyleButtonRow(items = Styles, selectedUpdate = {
            if(it == "insertImage"){
                pickImageLauncher.launch(arrayOf("image/*"))
            }
            else if(it == "insertPhoto"){

                val file = context.createImageFile()
                uri = FileProvider.getUriForFile(
                    Objects.requireNonNull(context),
                    BuildConfig.APPLICATION_ID + ".provider", file
                )
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uri)
                } else {
                    // Request a permission
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }

            }
            else{
                onStyleButtonClick(richEditorComposeView, it)
            }
        })
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
}