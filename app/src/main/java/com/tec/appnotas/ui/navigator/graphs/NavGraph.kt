package com.tec.appnotas.ui.navigator.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.main.Graphs
import com.tec.appnotas.ui.navigator.main.HomeScreenContainer

@Composable
fun RootGraph(globalProvider: GlobalProvider){
    NavHost(
        navController = globalProvider.nav,
        startDestination = Graphs.HomeGraph.route
    ){
        composable(route = Graphs.HomeGraph.route) {
           HomeScreenContainer(globalProvider = globalProvider)
        }
    }
}