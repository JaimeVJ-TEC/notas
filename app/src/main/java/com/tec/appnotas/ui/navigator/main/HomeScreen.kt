package com.tec.appnotas.ui.navigator.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tec.appnotas.ui.components.CustomTopBar
import com.tec.appnotas.ui.components.DrawerBody
import com.tec.appnotas.ui.components.DrawerHead
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.graphs.HomeGraph
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContainer(navController: NavHostController = rememberNavController(), globalProvider: GlobalProvider){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var currentItem by remember { mutableStateOf("Simple Notes (-•◡•)")}

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 CustomTopBar(title = currentItem,
                     onNavClick = {
                         scope.launch{scaffoldState.drawerState.open()}
                     }
                 )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHead()
            DrawerBody(modifier = Modifier, navController) {
                currentItem = it
                scope.launch{scaffoldState.drawerState.close()}
            }
        }
    ){
        HomeGraph(navController = navController, globalProvider = globalProvider)
    }
}