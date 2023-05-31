package com.tec.appnotas.ui.navigator.main

import com.tec.appnotas.R

const val ARGUMENT_NOTAID = "id"

sealed class Screens(val route : String){
    object SplashScreen : Screens("splash_screen")
    object HomeScreen: Screens("NotasScreen")
    object NotaScreen: Screens("NotaScreen/{id}")
    object ScanScreen: Screens("ScanScreen")
}

sealed class ScaffoldScreen(val route : String,
                            val title: String,
                            val icon: Int
){

    object Home: ScaffoldScreen(
        route = "Notas",
        title = " Notas",
        R.drawable.notesicon
    )

    object Archivo: ScaffoldScreen(
        route = "Archivo",
        title = " Archivo",
        R.drawable.clipicon
    )

    object Calendario: ScaffoldScreen(
        route = "Calendario",
        title = " Calendario",
        R.drawable.calendaricon
    )

    object Opciones: ScaffoldScreen(
        route = "Opciones",
        title = " Opciones",
        R.drawable.toolicon
    )

    object Acerca: ScaffoldScreen(
        route = "Acerca",
        title = " Acerca de la App",
        R.drawable.abouticon
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
