package com.tec.appnotas.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tec.appnotas.R

@Composable
fun SinArchivedNotes() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.archivenotetwo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Parece que aún no hay notas archivadas 😕❓", style = MaterialTheme.typography.h6
        )
    }
}


@Composable
fun SinNotes() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(100.dp)
    ) {
        Image(
            modifier = Modifier
                .size(70.dp, 80.dp)
                .clip(shape = RoundedCornerShape(75.dp)),
            painter = painterResource(id = R.drawable.notesicon),
            contentDescription = "Sin notas",
            //colorFilter = ColorFilter.tint(Color.Black),

        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "Aún no existen notas 📝 ¡Crea una nueva haciendo tocando en el botón! ⭐",style = MaterialTheme.typography.h5)

    }
}
