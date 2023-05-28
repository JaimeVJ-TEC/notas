package com.tec.appnotas.ui.screens.calendario

import androidx.lifecycle.ViewModel
import com.tec.appnotas.domain.models.Event

class CalendarioViewModel : ViewModel() {
    val events = mutableListOf<Event>()
}
