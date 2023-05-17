package com.tec.appnotas.ui.screens.archivo

import androidx.compose.runtime.Composable
import com.tec.appnotas.R
import com.tec.appnotas.ui.global.GlobalProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ArchivoScreen(navController: NavHostController, globalProvider: GlobalProvider){
    ArchivedNotes()
}

@Composable
fun ArchivedNotes() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.archivenote),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Parece quea un no hay notas archivadas 😕❓")
    }
}
