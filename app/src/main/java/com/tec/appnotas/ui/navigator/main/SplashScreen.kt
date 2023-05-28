package com.tec.appnotas.ui.navigator.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tec.appnotas.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.popBackStack() //Para borrar el registro de navegacion, sirve para que no se pueda ir de nuevo hacia atras < o
        navController.navigate(route = ScaffoldScreen.Home.route)
    }
    Splash()
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Simple Notes", fontSize = 30.sp, modifier = Modifier.padding(bottom = 20.dp))
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "LogoSplash",
            modifier = Modifier.size(150.dp, 150.dp).clip(shape = RoundedCornerShape(75.dp)),
        )
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Powered by Android",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SplashScreenView() {
//    SplashScreen(navController)
//}
