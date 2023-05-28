package com.tec.appnotas.ui.screens.calendario

import androidx.lifecycle.ViewModel
import com.tec.appnotas.domain.models.Event
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.EventRepository
import com.tec.appnotas.domain.repository.NotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CalendarioViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    //CAMBIA A FLOW
    val events = mutableListOf<Event>()

//    TODO:
//    Metodos para insertar, modificar y eliminar eventos
}