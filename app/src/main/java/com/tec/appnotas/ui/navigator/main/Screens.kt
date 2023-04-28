package com.tec.appnotas.ui.navigator.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

const val ARGUMENT_NOTAID = "id"

sealed class Screens(val route : String){
    object HomeScreen: Screens("NotasScreen")
    object NotaScreen: Screens("NotaScreen")
}

sealed class ScaffoldScreen(val route : String,
                             val title: String,
                             val icon: ImageVector
){
    object Home: ScaffoldScreen(
        route = "Notas",
        title = "Notas",
        icon = Icons.Default.Home
    )

    object Archivo: ScaffoldScreen(
        route = "Archivo",
        title = "Archivo",
        icon = Icons.Default.AccountBox
    )

    object Calendario: ScaffoldScreen(
        route = "Calendario",
        title = "Calendario",
        icon = Icons.Default.DateRange
    )

    object Opciones: ScaffoldScreen(
        route = "Opciones",
        title = "Opciones",
        icon = Icons.Default.Settings
    )
}

sealed class Graphs(val route: String){
    object RootGraph: Graphs("RootGraph")
    object HomeGraph: Graphs("HomeGraph")
    object ListaGraph: Graphs("ListaGraph")
    object OpcionesGraph: Graphs("OpcionesGraph")
    object ArchivoGraph: Graphs("ArchivoGraph")
    object Calendario: Graphs("CalendarioGraph")
}