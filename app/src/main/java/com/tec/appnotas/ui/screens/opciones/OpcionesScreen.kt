package com.tec.appnotas.ui.screens.opciones

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tec.appnotas.ui.global.GlobalProvider
import kotlinx.coroutines.launch

@Composable
fun OpcionesScreen(
    navController: NavHostController,
    globalProvider: GlobalProvider
){

    var valorModoOscuro = globalProvider.dataStore.getDarkModeValue.collectAsState(initial = false).value
    var valorVistaPrevia = globalProvider.dataStore.getDescriptionValue.collectAsState(initial = false).value
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Row(modifier = Modifier.padding(15.dp)) {
            Column {
                Text(
                    text = "Configura Simple Notes. " +
                            "\n -🌓 Modo oscuro: Cambia el tema de la aplicacion para descansar tu vista en ambientes con poca luz. " +
                            "\n -👓 Vista Previa: Muestra u oculta el cuerpo de las notas, añade privacidad extra!",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
        RowOpcion(title = "Modo oscuro", checked = valorModoOscuro){
            coroutineScope.launch {
                globalProvider.dataStore.saveDarkModeValue(it)
            }
        }
        RowOpcion(title = "Vista previa", checked = valorVistaPrevia){
            coroutineScope.launch {
                globalProvider.dataStore.saveDescriptionValue(it)
            }
        }

    }
}

@Composable
fun RowOpcion(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(10.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector =  Icons.Rounded.NightlightRound,
            contentDescription = null )
        Text(text = title, fontSize =23.sp)
        Spacer(modifier = Modifier.width(15.dp))
        Switch(
            checked = checked,
            onCheckedChange = {onCheckedChange(it)},
        )
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(95.dp)
            .padding(top = 15.dp)
        ){
            Text(text = if (checked) "Activado" else "Desactivado")
        }
    }
}