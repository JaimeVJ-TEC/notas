package com.tec.appnotas.ui.global

import androidx.navigation.NavHostController
import com.tec.appnotas.domain.datasource.UserStore
import com.tec.appnotas.ui.screens.calendario.CalendarioViewModel
import com.tec.appnotas.ui.screens.notas.UserViewmodel

data class GlobalProvider(
    val nav: NavHostController,
    val userVM: UserViewmodel,
    val calendarioVM: CalendarioViewModel,
    val dataStore: UserStore
)