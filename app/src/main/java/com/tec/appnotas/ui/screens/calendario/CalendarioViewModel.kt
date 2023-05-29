package com.tec.appnotas.ui.screens.calendario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tec.appnotas.domain.models.Event
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.repository.EventRepository
import com.tec.appnotas.domain.repository.NotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarioViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    //CAMBIA A FLOW
    val events: Flow<List<Event>> = eventRepository.getAllEvents()

    init{

    }

    fun insertEvento(event: Event){
        viewModelScope.launch {
            eventRepository.insertLocalEvent(event)
        }
    }

    fun modifiyEvento(event: Event){
        viewModelScope.launch {
            eventRepository.updateLocalEvent(event)
        }
    }

    fun deleteEvento(event: Event){
        viewModelScope.launch {
            eventRepository.deleteLocalEvent(event)
        }
    }
}