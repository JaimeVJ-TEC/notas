package com.tec.appnotas

import com.tec.appnotas.domain.dao.EventoDao
import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.models.Event
import com.tec.appnotas.domain.models.Nota
import kotlinx.coroutines.flow.Flow

private val nota1 = Nota(1,"MockNota1","MockNota1Content",false)
private val nota2 = Nota(2,"MockNota2","MockNota2Content",true)

class MockNotaDao: NotaDao {

    override suspend fun insertNota(nota: Nota): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateNota(nota: Nota) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNota(nota: Nota) {
        TODO("Not yet implemented")
    }

    override fun getAllNotas(): Flow<List<Nota>> {
        TODO("Not yet implemented")
    }

    override fun getNotaById(id: Int): Nota {
        TODO("Not yet implemented")
    }

    override fun getArchivedNotas(archived: Boolean): Flow<List<Nota>> {
        TODO("Not yet implemented")
    }

}

class MockEventoDao: EventoDao{
    override suspend fun insertEvento(evento: Event): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateEvento(evento: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvento(evento: Event) {
        TODO("Not yet implemented")
    }

    override fun getAllEventos(): Flow<List<Event>> {
        TODO("Not yet implemented")
    }

    override suspend fun getEventById(id: Int): Event {
        TODO("Not yet implemented")
    }

}