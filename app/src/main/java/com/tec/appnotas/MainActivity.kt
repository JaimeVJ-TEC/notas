package com.tec.appnotas

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.tec.appnotas.domain.datasource.UserStore
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.graphs.RootGraph
import com.tec.appnotas.ui.screens.calendario.CalendarioViewModel
import com.tec.appnotas.ui.screens.notas.UserViewmodel
import com.tec.appnotas.ui.theme.AppnotasTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContent {
            val store = UserStore(LocalContext.current)
            val navController = rememberNavController()
            val userVM : UserViewmodel = hiltViewModel()
            val calendarioVM : CalendarioViewModel = hiltViewModel()

            val gp = GlobalProvider(
                nav = navController,
                userVM,
                calendarioVM,
                store
            )

            val darkmode = gp.dataStore.getDarkModeValue.collectAsState(initial = false).value
//            val locale = gp.dataStore.getLanguageValue.collectAsState(initial = "es").value
//            Locale.setDefault(Locale(locale))
//            Locale.setDefault(Locale("en"))
//            var changed = false

            AppnotasTheme(darkTheme = darkmode) {
                // A surface container using the 'background' color from the theme
                mainScreen(gp = gp)
            }
        }
    }
}

@Composable
fun mainScreen(gp: GlobalProvider){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        RootGraph(globalProvider = gp)
    }
}
