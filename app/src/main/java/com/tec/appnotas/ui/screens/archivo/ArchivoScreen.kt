package com.tec.appnotas.ui.screens.archivo

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.Screens
import com.tec.appnotas.ui.screens.notas.AddButton
import com.tec.appnotas.ui.screens.notas.ListaNotas
import com.tec.appnotas.ui.screens.notas.QRButton
import com.tec.appnotas.ui.screens.notas.getResumen
import kotlinx.coroutines.launch

@Composable
fun ArchivoScreen(navController: NavHostController, globalProvider: GlobalProvider){
    val notas = globalProvider.userVM.listaNotasArchived.collectAsState(listOf()).value
    ListaNotasArchived(lista = notas,navController,globalProvider)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaNotasArchived(lista: List<Nota>, navController: NavHostController, globalProvider: GlobalProvider) {
    val activeItem = remember { mutableStateOf<Int?>(null) }
    var showButtons by remember { mutableStateOf(false ) }

    Box(modifier = Modifier.fillMaxSize(1f)) {
        LazyColumn() {
            item {
                lista.forEach { item ->

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    activeItem.value = item.notaId; Log.d(
                                    "ACTIVEITEM",
                                    item.notaId.toString()
                                )
                                    showButtons = true
                                }
                            )
                    ) {
                        Column() {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .background(color = Color.Green)
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

        Column(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
            if(showButtons && activeItem.value != null) {
                BottomRowArchived(
                    modifier = Modifier.fillMaxWidth(),
                    onRestoreClick =
                    {
                        globalProvider.userVM.archiveNota(activeItem.value!!,false)
                    },
                    onDeleteClick =
                    {
                        globalProvider.userVM.deleteNota(activeItem.value!!)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomRowArchived(modifier: Modifier, onRestoreClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = onRestoreClick, modifier = Modifier.weight(1f)) {
            Text("Restore")
        }
        Button(onClick = onDeleteClick, modifier = Modifier.weight(1f)) {
            Text("Delete")
        }
    }
}