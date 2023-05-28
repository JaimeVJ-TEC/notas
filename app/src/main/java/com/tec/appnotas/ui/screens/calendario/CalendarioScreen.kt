package com.tec.appnotas.ui.screens.calendario

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tec.appnotas.ui.components.MiCalendario
import com.tec.appnotas.ui.global.GlobalProvider

@Composable
fun CalendarioScreen(navController: NavHostController, globalProvider: GlobalProvider) {
    val viewModel = viewModel<CalendarioViewModel>()
    MiCalendario(viewModel = viewModel)
}
