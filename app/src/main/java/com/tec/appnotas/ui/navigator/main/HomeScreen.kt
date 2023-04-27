package com.tec.appnotas.ui.navigator.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tec.appnotas.data.constants.DrawerItems
import com.tec.appnotas.ui.components.CustomTopBar
import com.tec.appnotas.ui.components.DrawerBody
import com.tec.appnotas.ui.components.DrawerHead
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(/*navController: NavHostController, globalProvider: GlobalProvider?*/){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 CustomTopBar(title = "XD",
                     onNavClick = {
                         scope.launch{scaffoldState.drawerState.open()}
                     }
                 )
        },
        drawerContent = {
            DrawerHead()
            DrawerBody(items = DrawerItems,modifier = Modifier, textStyle = null,
                onItemClick = {println(it.id)})
        }
    ){

    }
}