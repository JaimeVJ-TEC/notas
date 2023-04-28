package com.tec.appnotas.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.tec.appnotas.ui.navigator.main.ScaffoldScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DrawerHead(){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(text="HEADER",fontSize = 45.sp)
    }
}

@Composable
fun DrawerBody(
    modifier: Modifier,
    navController: NavHostController,
    onClick: (String) -> Unit
){
    val screens = listOf(
        ScaffoldScreen.Home,
        ScaffoldScreen.Archivo,
        ScaffoldScreen.Calendario,
        ScaffoldScreen.Opciones
    )

    LazyColumn(modifier){
        items(screens){ item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(item.route){
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                        onClick(item.title)
                    }
                    .padding(16.dp)
            ){
                Icon(imageVector = item.icon!!, contentDescription = item.title)
                Text(text = item.title, modifier = Modifier.weight(1f))
            }

        }
    }
}
