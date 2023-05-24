package com.tec.appnotas.ui.screens.notas

import android.Manifest
import android.content.pm.PackageManager
import android.text.Html
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.Screens
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun NotasListScreen(navController: NavHostController, globalProvider: GlobalProvider){
    val notas = globalProvider.userVM.listaNotas.collectAsState().value
    ListaNotas(lista = notas,navController,globalProvider)
}

@Composable
fun ListaNotas(lista: List<Nota>, navController: NavHostController,globalProvider: GlobalProvider) {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            navController.navigate(Screens.ScanScreen.route)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn() {
            item {
                lista.forEach { item ->

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { navController.navigate( route = "NotaScreen/${item.notaId}") }
                    ) {
                        Column() {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .background(color = androidx.compose.ui.graphics.Color.Green)
                                        .height(10.dp)
                                        .width(10.dp)
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(text = item.title, fontSize = 20.sp)
                            }
                            Text(text = getResumen(item.content), fontSize = 13.sp)
                        }
                    }
                    Divider()
                }
            }
        }
        AddButton(modifier = Modifier.align(Alignment.BottomEnd)){
            coroutineScope.launch {
                val nota = globalProvider.userVM.insertNota()
                navController.navigate(route = "NotaScreen/${nota.notaId}")
            }
        }

        QRButton(modifier = Modifier.align(Alignment.BottomStart)){
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                navController.navigate(Screens.ScanScreen.route)
            } else {
                // Request a permission
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

}

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
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Note",
            modifier = Modifier.size(24.dp)
        )
    }
}

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

fun getResumen(contenido: String): String{
    var text = HtmlCompat.fromHtml(contenido.replace(Regex("<img[^>]*>"), ""),HtmlCompat.FROM_HTML_MODE_LEGACY).toString().replace("\n", "").replace("\r", "");
    return if (text.length > 50) {
        text.substring(0, 50)
    } else {
        text
    }
}