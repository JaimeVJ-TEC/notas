package com.tec.appnotas.ui.screens.notas

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.Screens

@Composable
fun NotasListScreen(navController: NavHostController, globalProvider: GlobalProvider){
    Column() {
        Text("Lista de Notas")
        Button(onClick = { navController.navigate(Screens.NotaScreen.route) }) {
            Text(text = "NOTA")
        }
    }
}