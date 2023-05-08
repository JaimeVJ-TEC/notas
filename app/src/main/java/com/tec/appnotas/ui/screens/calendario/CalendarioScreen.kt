package com.tec.appnotas.ui.screens.calendario

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.tec.appnotas.ui.global.GlobalProvider
import io.github.boguszpawlowski.composecalendar.StaticCalendar


@Composable
fun CalendarioScreen(navController: NavHostController, globalProvider: GlobalProvider) {
    StaticCalendar()
}
