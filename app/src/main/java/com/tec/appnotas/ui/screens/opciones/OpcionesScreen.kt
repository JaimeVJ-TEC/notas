package com.tec.appnotas.ui.screens.opciones

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tec.appnotas.R
import com.tec.appnotas.ui.global.GlobalProvider
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun OpcionesScreen(
    navController: NavHostController,
    globalProvider: GlobalProvider
){

    var valorModoOscuro = globalProvider.dataStore.getDarkModeValue.collectAsState(initial = false).value
    var valorVistaPrevia = globalProvider.dataStore.getDescriptionValue.collectAsState(initial = false).value
    var valorLanguage = globalProvider.dataStore.getLanguageValue.collectAsState(initial = "es").value

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Row(modifier = Modifier.padding(15.dp)) {
            Column {
                Text(
                    text = stringResource(R.string.options_instruct_start) +"\n\n" + stringResource(
                                            R.string.options_instruct_darkmode),
                            //"\n -👓 Vista Previa: Muestra u oculta el cuerpo de las notas, añade privacidad extra!",
                    style = MaterialTheme.typography.h4,
                )
            }
        }
        RowOpcion(title = stringResource(R.string.options_darkmode), checked = valorModoOscuro){
            coroutineScope.launch {
                globalProvider.dataStore.saveDarkModeValue(it)
            }
        }
        RowOpcion(title = stringResource(R.string.options_preview), checked = valorVistaPrevia){
            coroutineScope.launch {
                globalProvider.dataStore.saveDescriptionValue(it)
            }
        }

//        LanguageDropdown(languages = locales, selectedLanguage = Locale(valorLanguage), onLanguageSelected = {
//            coroutineScope.launch {
//                globalProvider.dataStore.saveLanguageValue(it.language)
//            }
//        })

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
            Text(text = if (checked) stringResource(R.string.option_row_enabled) else stringResource(
                            R.string.option_row_disable)
                        )
        }
    }
}

@Composable
fun LanguageDropdown(
    languages: List<Locale>,
    selectedLanguage: Locale,
    onLanguageSelected: (Locale) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedIndex = languages.indexOf(selectedLanguage)

    Box(modifier = Modifier.wrapContentSize()) {
        Text(
            text = selectedLanguage.displayName,
            modifier = Modifier.clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            languages.forEachIndexed { index, language ->
                DropdownMenuItem(onClick = {
                    onLanguageSelected(language)
                    expanded = false
                }) {
                    Text(
                        text = language.displayName,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

val locales = listOf(
    Locale("es"),
    Locale("en")
)