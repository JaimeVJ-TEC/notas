package com.tec.appnotas

import NotasViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.navigator.graphs.RootGraph
import com.tec.appnotas.ui.theme.AppnotasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppnotasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val gp = GlobalProvider(
                        nav = navController
                    )
                    RootGraph(globalProvider = gp)
                }
            }
        }
    }
}

@Composable
fun test(viewModel: NotasViewModel = hiltViewModel()){
    Button(onClick = {
        viewModel.getNota("9de78c0c-78cb-4d0f-b3ef-7e5d23ee0c5c")
    }){

    }
}
