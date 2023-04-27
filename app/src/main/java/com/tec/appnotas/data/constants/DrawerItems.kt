package com.tec.appnotas.data.constants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.tec.appnotas.ui.components.DrawerItem

val DrawerItems = listOf(
    DrawerItem("notas","Notas", Icons.Default.Home,"Notas"),
    DrawerItem("archivo","Archivo",Icons.Default.AccountBox,"Archivos"),
    DrawerItem("calendario","Calendario",Icons.Default.DateRange,"Calendario"),
    DrawerItem("opciones","Opciones",Icons.Default.Settings,"Opciones")
)