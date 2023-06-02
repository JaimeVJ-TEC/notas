package com.tec.appnotas.ui.screens.notas

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import com.tec.appnotas.R
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.ui.components.SinArchivedNotes
import com.tec.appnotas.ui.components.SinNotes
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.Screens
import com.tec.appnotas.ui.theme.AzulClaro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun NotasListScreen(navController: NavHostController, globalProvider: GlobalProvider){
    val notas = globalProvider.userVM.listaNotas.collectAsState(listOf()).value
    ListaNotas(lista = notas,navController,globalProvider)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaNotas(lista: List<Nota>, navController: NavHostController,globalProvider: GlobalProvider) {
    val showDescription = globalProvider.dataStore.getDescriptionValue.collectAsState(initial = true).value
    val vmState = globalProvider.userVM.userState.collectAsState().value

    val coroutineScope = rememberCoroutineScope()
    val activeItem = remember { mutableStateOf<Int?>(null) }
    var showButtons by remember { mutableStateOf(false )}
    val showDialog = remember { mutableStateOf(false) }
    val saveBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val density = LocalDensity.current
    var LaunchScan by remember { mutableStateOf(false)}

    val permissionSuccess = stringResource(id = R.string.permission_success)
    val permissionFailure = stringResource(R.string.permission_failure)
    val connectionError = stringResource(R.string.connection_error)
    
    //PERMISOS PARA LA CAMARA, SI SE DAN PERMISOS ENTONCES SE NAVEGA A LA PANTALLA DE ESCANEO
    val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, permissionSuccess, Toast.LENGTH_SHORT).show()
            if(LaunchScan) {
                navController.navigate(Screens.ScanScreen.route)
            }else{
                coroutineScope.launch(Dispatchers.IO) {
                    saveBitmap.value = globalProvider.userVM.shareNota(activeItem.value!!)
                    showDialog.value = true
                }
            }
        } else {
            Toast.makeText(context, permissionFailure, Toast.LENGTH_SHORT).show()
        }
    }

    if(vmState == UserVMState.CONNECTION_ERROR){
        Toast.makeText(context, connectionError, Toast.LENGTH_SHORT).show()
        globalProvider.userVM.notifiedError()
    }

    //Caja de notas
    Box(modifier = Modifier.fillMaxSize(1f)) {
        if (lista.isEmpty()){
            SinNotes()
        }
        else{
            //LAZY COLUMN CON LAS NOTAS NO ARCHIVADAS
            LazyColumn() {
                item() {
                    lista.forEach { item ->
                        Box(modifier = Modifier.background(
                            if(activeItem.value != null && item.notaId == activeItem.value){
                                MaterialTheme.colors.primaryVariant
                            }
                            else{
                                MaterialTheme.colors.surface
                            })) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .combinedClickable(
                                        onClick = { navController.navigate(route = "NotaScreen/${item.notaId}") },
                                        onLongClick = {
                                            if (activeItem.value != null && activeItem.value == item.notaId) {
                                                activeItem.value = null
                                                showButtons = false
                                            } else {
                                                activeItem.value = item.notaId;
                                                showButtons = true
                                            }
                                        }
                                    )
                            ) {
                                //Caja de colores de nota
                                Column() {
                                    Row {
                                        Box(
                                            modifier = Modifier
                                                .background(color = androidx.compose.ui.graphics.Color.Green)
                                                .height(10.dp)
                                                .width(10.dp)
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(text = item.title, fontSize = 20.sp,modifier = Modifier.testTag("list_title"))
                                    }
                                    //poner un if para la vista previa
                                    if(showDescription) {
                                        Text(text = getResumen(item.content), fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                        Divider()
                    }
                }
            }
        }


        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()) {

            //LOS BOTONES DE AGREGAR NUEVA NOTA Y ESCANEAR CODIGO QR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QRButton(modifier = Modifier) {
                    LaunchScan = true
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        navController.navigate(Screens.ScanScreen.route)
                    } else {
                        // Request a permission
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }

                AddButton(modifier = Modifier) {
                    coroutineScope.launch {
                        val nota = globalProvider.userVM.insertNota(Nota())
                        navController.navigate(route = "NotaScreen/${nota.notaId}")
                    }
                }
            }
//ANIMACION
            AnimatedVisibility(
                visible = showButtons && activeItem.value != null,
                enter = slideInVertically {
                    with(density) { 40.dp.roundToPx() }
                } + expandVertically(
                    expandFrom = Alignment.Bottom
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                BottomRow(
                    modifier = Modifier.fillMaxWidth(),
                    onShareClick =
                    {
                        LaunchScan = false
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            coroutineScope.launch(Dispatchers.IO) {
                                saveBitmap.value = globalProvider.userVM.shareNota(activeItem.value!!)
                                showDialog.value = saveBitmap.value != null
                            }
                        } else {
                            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    },
                    onDeleteClick =
                    {
                        globalProvider.userVM.deleteNota(activeItem.value!!)
                        activeItem.value = null
                        showButtons = false
                    },
                    onArchiveClick =
                    {
                        globalProvider.userVM.archiveNota(activeItem.value!!,true)
                        activeItem.value = null
                        showButtons = false
                    }
                )

            }
        }


        PopUpQRCode(
            show = showDialog.value,
            context = context,
            onDismiss = {showDialog.value = false },
            onClick = { showDialog.value = false },
            onSave = {showDialog.value = false},
            QRCode = saveBitmap.value
        )
    }
}

//LOS BOTONES DE ARCHIVAR, ELIMINAR Y COMPARTIR
@Composable
fun BottomRow(modifier: Modifier, onShareClick: () -> Unit, onDeleteClick: () -> Unit, onArchiveClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp)),
            //.background(MaterialTheme.colors.surface),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = onShareClick,
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.row_button_share), color = MaterialTheme.colors.onSurface)
        }

        Button(
            onClick = onDeleteClick,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.row_button_delete), color = MaterialTheme.colors.onError)
        }

        Button(
            onClick = onArchiveClick,
            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.row_button_archive), color = MaterialTheme.colors.onSurface)
        }
    }
}

//BOTON PARA AGREGAR NOTAS
@Composable
fun AddButton(modifier: Modifier,onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier
            .padding(16.dp)
            .size(56.dp)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colors.surface)
            .testTag("Add")
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Note",
            modifier = Modifier.size(24.dp)
        )
    }
}


//BOTON PARA ESCANEAR CODIGO QR
@Composable
fun QRButton(modifier: Modifier,onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier
            .padding(16.dp)
            .size(56.dp)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colors.surface)
    ) {
        Icon(
            imageVector = Icons.Default.QrCodeScanner,
            contentDescription = "Scan QR Code",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun PopUpQRCode(show: Boolean, context: Context, onDismiss: () -> Unit, onClick: () -> Unit, onSave: () -> Unit, QRCode: Bitmap?){
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(R.string.alert_qr_code)) },
            text = {
                Column {
                    if(QRCode != null) {
                        Image(bitmap = QRCode.asImageBitmap(), contentDescription = "Codigo QR")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        if(QRCode != null) {
                            saveImage(bitmap = QRCode,context,"TecNotas")
                            onSave()
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.alert_save))
                }
            }
        )
    }
}


//FUNCION QUE OBTIENE UN CORTO RESUMEN DE LAS NOTAS, PARA CAMBIAR LA LONGITUD DEL RESUMEN CAMBIA EL NUMERO [50]
fun getResumen(contenido: String): String{
    var text = HtmlCompat.fromHtml(contenido.replace(Regex("<img[^>]*>"), ""),HtmlCompat.FROM_HTML_MODE_LEGACY).toString().replace("\n", "").replace("\r", "");
    return if (text.length > 50) {
        text.substring(0, 50)
    } else {
        text
    }
}

private fun saveImage(bitmap: Bitmap, context: Context, folderName: String) {
    if (android.os.Build.VERSION.SDK_INT >= 29) {
        val values = contentValues()
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$folderName")
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        // RELATIVE_PATH and IS_PENDING are introduced in API 29.

        val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            context.contentResolver.update(uri, values, null, null)
        }
    } else {
        val directory = File(Environment.getExternalStorageDirectory().toString() + separator + folderName)
        // getExternalStorageDirectory is deprecated in API 29

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = System.currentTimeMillis().toString() + ".png"
        val file = File(directory, fileName)
        saveImageToStream(bitmap, FileOutputStream(file))
        if (file.absolutePath != null) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }
    }
}

private fun contentValues() : ContentValues {
    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
    return values
}

private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
    if (outputStream != null) {
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}