package com.tec.appnotas.ui.navigator.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

const val ARGUMENT_NOTAID = "id"

sealed class Screens(val route : String){
    object SplashScreen : Screens("splash_screen")
    object HomeScreen: Screens("NotasScreen")
    object NotaScreen: Screens("NotaScreen/{id}")
    object ScanScreen: Screens("ScanScreen")
}

sealed class ScaffoldScreen(val route : String,
                            val title: String,
                            val icon: ImageVector
){

    object Home: ScaffoldScreen(
        route = "Notas",
        title = " Notas",
        icon = Icons.Default.Home
    )

    object Archivo: ScaffoldScreen(
        route = "Archivo",
        title = " Archivo",
        icon = Icons.Default.AccountBox
    )

    object Calendario: ScaffoldScreen(
        route = "Calendario",
        title = " Calendario",
        icon = Icons.Default.DateRange
    )

    object Opciones: ScaffoldScreen(
        route = "Opciones",
        title = " Opciones",
        icon = Icons.Default.Settings
    )

    object Acerca: ScaffoldScreen(
        route = "Acerca",
        title = " Acerca de la App",
        icon = Icons.Default.Info
    )
}

sealed class Graphs(val route: String){
    object RootGraph: Graphs("RootGraph")
    object HomeGraph: Graphs("HomeGraph")
    object ListaGraph: Graphs("ListaGraph")
    object OpcionesGraph: Graphs("OpcionesGraph")
    object ArchivoGraph: Graphs("ArchivoGraph")
    object CalendarioGraph: Graphs("CalendarioGraph")

}
