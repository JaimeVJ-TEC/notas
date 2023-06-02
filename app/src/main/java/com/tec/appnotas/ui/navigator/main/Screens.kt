package com.tec.appnotas.ui.navigator.main

import androidx.annotation.StringRes
import com.tec.appnotas.R

const val ARGUMENT_NOTAID = "id"

sealed class Screens(val route : String){
    object SplashScreen : Screens("splash_screen")
    object HomeScreen: Screens("NotasScreen")
    object NotaScreen: Screens("NotaScreen/{id}")
    object ScanScreen: Screens("ScanScreen")
}

sealed class ScaffoldScreen(val route : String,
                            @StringRes val title:Int,
                            val icon: Int
){

    object Home: ScaffoldScreen(
        route = "Notas",
        title = R.string.home_title,
        R.drawable.notesicon
    )

    object Archivo: ScaffoldScreen(
        route = "Archivo",
        title = R.string.archivo_title,
        R.drawable.clipicon
    )

    object Calendario: ScaffoldScreen(
        route = "Calendario",
        title = R.string.calendario_title,
        R.drawable.calendaricon
    )

    object Opciones: ScaffoldScreen(
        route = "Opciones",
        title = R.string.opciones_title,
        R.drawable.toolicon
    )

    object Acerca: ScaffoldScreen(
        route = "Acerca",
        title = R.string.acerca_title,
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
