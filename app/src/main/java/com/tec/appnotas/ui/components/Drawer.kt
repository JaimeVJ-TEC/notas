package com.tec.appnotas.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.tec.appnotas.R
import com.tec.appnotas.ui.navigator.main.ScaffoldScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerHead(){
    Box(
        modifier = Modifier.fillMaxWidth(),
    ){
        Image(
            painter = painterResource(id = R.drawable.noteheader),
            contentDescription = "Logo",
            modifier = Modifier
                .size(400.dp, 200.dp)
                .align(Alignment.TopStart),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "Bienvenido Juan 🐎!",
            color = Color.White,
            textAlign = TextAlign.Right,
            style = TextStyle(fontSize = 30.sp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)

        )
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
                        navController.navigate(item.route) {
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
