package com.tec.appnotas.ui.screens.opciones

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tec.appnotas.ui.global.GlobalProvider


@Composable
fun OpcionesScreen(navController: NavHostController, globalProvider: GlobalProvider){

    //posible error Modo oscuro simpre comienza con FALSO
    var valorModoOscuro by remember { mutableStateOf(false) }
    var valorAgregarPIN by remember { mutableStateOf(false) }
    var valorVistaPrevia by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(10.dp)
                .border(width = 1.dp, color = Color.LightGray,shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(16.dp)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector =  Icons.Rounded.NightlightRound,
                contentDescription = null )
            Text(text = "Modo oscuro", fontSize =23.sp)
            Spacer(modifier = Modifier.width(15.dp))
            Switch(
                checked = valorModoOscuro,
                onCheckedChange = { valorModoOscuro = it },
            )
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(95.dp)
                .padding(top = 15.dp)
            ){
                Text(text = if (valorModoOscuro) "Activado" else "Desactivado")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(10.dp)
                .border(width = 1.dp, color = Color.LightGray,shape = RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(16.dp)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(imageVector =  Icons.Rounded.Lock,
                contentDescription = null )

            Text(text = "Agregar PIN", fontSize =23.sp)

            Spacer(modifier = Modifier.width(15.dp))
            Switch(
                checked = valorAgregarPIN,
                onCheckedChange = { valorAgregarPIN = it },
            )
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(95.dp)
                .padding(top = 15.dp)
            ){
                Text(text = if (valorAgregarPIN) "   Activado" else "Desactivado")
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(10.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15.dp)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector =  Icons.Rounded.Article,
                contentDescription = null )

            Text(text = "Vista previa", fontSize =23.sp)

            Spacer(modifier = Modifier.width(15.dp))

            Switch(
                checked = valorVistaPrevia,
                onCheckedChange = { valorVistaPrevia = it },
            )

            Box(modifier = Modifier
                .fillMaxHeight()
                .width(95.dp)
                .padding(top = 15.dp)
            ){
                Text(text = if (valorVistaPrevia) "   Activado" else "Desactivado")
            }
        }
    }
}
