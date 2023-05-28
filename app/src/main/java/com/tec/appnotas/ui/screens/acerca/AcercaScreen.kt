package com.tec.appnotas.ui.screens.acerca

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.tec.appnotas.ui.global.GlobalProvider
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tec.appnotas.R

@Composable
fun AcercaScreen(navController: NavHostController, globalProvider: GlobalProvider) {
    val cardColor = MaterialTheme.colors.surface
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                backgroundColor = cardColor,
                elevation = 5.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(64.dp).padding(5.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Simple Notes (-•◡•)📝",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(5.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                backgroundColor = cardColor,
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(
                        text = """
                            ✏️💭 ¡Simple Notes! - La forma más sencilla de capturar tus ideas y pensamientos.

                            📝✨ Simplicidad en tus manos:
                            Con una interfaz intuitiva y fácil de usar, podrás crear y organizar tus notas en segundos.

                            📍🔔 Recordatorios inteligentes:
                            Nunca olvides una tarea o evento importante. Nuestra función de recordatorios te mantendrá en el buen camino y te avisará en el momento adecuado. ¡No más estrés por olvidos!

                            🌄📷 Captura y guarda tus momentos: 
                            No importa dónde te encuentres, con Simple Notes (-•◡•) puedes tomar fotos al instante y adjuntarlas a tus notas. Inmortaliza esos hermosos paisajes, momentos especiales o cualquier cosa que desees recordar.

                            🌙🌓 Interfaz intuitiva y modo oscuro: 
                            Navega por la aplicación sin esfuerzo y disfruta de una experiencia visualmente atractiva con nuestro elegante modo oscuro. Tanto si eres un búho nocturno como si te encanta el brillo del día, tenemos lo que necesitas.

                            🌐👥 Comparte tus notas!: 
                            Comparte tus notas con otros dispositivos, con Simple Notes tus ideas siempre estarán al alcance de tu mano!
                            
                            Descarga Simple Notes en la PlayStore! Y lleva tus notas al siguiente nivel. 🚀💡📱💫
                        """.trimIndent(),
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}





