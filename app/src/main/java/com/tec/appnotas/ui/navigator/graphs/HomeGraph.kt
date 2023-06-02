package com.tec.appnotas.ui.navigator.graphs

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tec.appnotas.domain.models.Nota
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
import com.tec.appnotas.ui.screens.acerca.AcercaScreen
import com.tec.appnotas.ui.screens.notas.scanner.ScanScreen

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
            BackHandler(true) {
                globalProvider.nav.popBackStack()
            }
            NotasListScreen(navController, globalProvider)
        }

        composable(route = Screens.NotaScreen.route,
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            })
        ){
            NotaScreen(navController,globalProvider, it.arguments!!.getInt("id"))
        }

        composable(route = Screens.ScanScreen.route){
            BackHandler(true) {
            }
            ScanScreen(globalProvider = globalProvider, navHostController = navController)
        }

        composable(route = ScaffoldScreen.Archivo.route){
            BackHandler(true) {
            }
            ArchivoScreen(navController, globalProvider)
        }

        composable(route = ScaffoldScreen.Calendario.route){
            BackHandler(true) {
            }
            CalendarioScreen(navController, globalProvider)
        }

        composable(route = ScaffoldScreen.Opciones.route){
            BackHandler(true) {
            }
            OpcionesScreen(navController, globalProvider)
        }

        composable(route = ScaffoldScreen.Acerca.route){
            BackHandler(true) {
            }
            AcercaScreen(navController, globalProvider)
        }
    }
}