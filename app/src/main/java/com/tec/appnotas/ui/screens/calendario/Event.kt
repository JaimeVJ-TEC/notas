package com.tec.appnotas.ui.screens.calendario

data class Event(
    val title: String,
    val eventBody: String,
    val selectedDay: Int,
    val currentMonth: Int
)
