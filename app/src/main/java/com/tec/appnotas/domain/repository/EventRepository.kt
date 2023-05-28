package com.tec.appnotas.domain.repository

import com.tec.appnotas.domain.dao.EventoDao
import com.tec.appnotas.domain.models.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EventRepository {
    suspend fun getAllEvents(): Flow<List<Event>>
    suspend fun getEventById(id: Int): Event
    suspend fun insertLocalEvent(event: Event) : Int
    suspend fun updateLocalEvent(event: Event)
    suspend fun deleteLocalEvent(event: Event)
}


class EventRepositoryImp @Inject constructor(
    private val eventoDao: EventoDao
) : EventRepository{
    override suspend fun getAllEvents(): Flow<List<Event>> {
        return  eventoDao.getAllEventos()
    }


    override suspend fun getEventById(id: Int): Event {
        return eventoDao.getEventById(id)
    }

    override suspend fun insertLocalEvent(event: Event): Int {
        return eventoDao.insertEvento(event).toInt()
    }

    override suspend fun updateLocalEvent(event: Event) {
        eventoDao.updateEvento(event)
    }

    override suspend fun deleteLocalEvent(event: Event) {
        eventoDao.deleteEvento(event)
    }
}