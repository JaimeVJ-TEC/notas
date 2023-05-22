package com.tec.appnotas.ui.screens.notas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.Screens

@Composable
fun NotasListScreen(navController: NavHostController, globalProvider: GlobalProvider){
//    val notas = userVM.listaNotas.collectAsState().value
//    ListaNotas(lista = notas,navController)
}

@Composable
fun ListaNotas(lista: List<Nota>, navController: NavHostController) {

    LazyColumn() {
        item {
            lista.forEach { item ->

                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { navController.navigate(Screens.NotaScreen.route) }
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

}

//val nota1 = Nota("Nota 1", "este es un ejemplo de la nota 2")
//val nota2 = Nota("Nota 2", "este es un ejemplo de la nota 2")
//val nota3 = Nota("Nota 3", "este es un ejemplo de la nota 3")
//
//data class Nota (
//    var titulo: String = "",
//    var contenido: String = ""
//){
//    fun getResumen(): String{
//        var text = contenido
//        return if (text.length > 50) {
//            text.substring(0, 50)
//        } else {
//            text
//        }
//    }
//}

fun getResumen(contenido: String): String{
    var text = contenido
    return if (text.length > 50) {
        text.substring(0, 50)
    } else {
        text
    }
}