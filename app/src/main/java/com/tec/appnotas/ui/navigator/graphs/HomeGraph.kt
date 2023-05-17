package com.tec.appnotas.ui.navigator.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.Graphs
import com.tec.appnotas.ui.navigator.main.ScaffoldScreen
import com.tec.appnotas.ui.navigator.main.Screens
import com.tec.appnotas.ui.screens.archivo.ArchivoScreen
import com.tec.appnotas.ui.screens.calendario.CalendarioScreen
import com.tec.appnotas.ui.screens.notas.NotasListScreen
import com.tec.appnotas.ui.screens.notas.editor.NotaScreen
import com.tec.appnotas.ui.screens.opciones.OpcionesScreen
import com.tec.appnotas.ui.navigator.main.SplashScreen
import com.tec.appnotas.ui.screens.notas.Nota

@Composable
fun HomeGraph(navController: NavHostController, globalProvider: GlobalProvider){
    NavHost(
        navController = navController,
        route = Graphs.HomeGraph.route,
        startDestination = Screens.SplashScreen.route
    ){
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = ScaffoldScreen.Home.route){
            NotasListScreen(navController, globalProvider)
        }

        composable(route = Screens.NotaScreen.route){
            NotaScreen(navController,globalProvider, Nota("Nota 1"))
        }

        composable(route = ScaffoldScreen.Archivo.route){
            ArchivoScreen(navController, globalProvider)
        }

        composable(route = ScaffoldScreen.Calendario.route){
            CalendarioScreen(navController, globalProvider)
        }

        composable(route = ScaffoldScreen.Opciones.route){
            OpcionesScreen(navController, globalProvider)
        }
    }
}