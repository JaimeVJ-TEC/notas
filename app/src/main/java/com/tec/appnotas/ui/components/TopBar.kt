package com.tec.appnotas.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tec.appnotas.data.constants.TopBarColor

@Composable
fun CustomTopBar(title: String, onNavClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.h2, color = Color.White) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = { onNavClick() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Button")
            }
        }
    )
}