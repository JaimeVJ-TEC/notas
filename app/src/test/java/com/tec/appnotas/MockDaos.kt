package com.tec.appnotas

import androidx.lifecycle.MutableLiveData
import com.tec.appnotas.domain.dao.EventoDao
import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.models.Event
import com.tec.appnotas.domain.models.Nota
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

private val nota1 = Nota(1,"MockNota1","MockNota1Content",false)
private val nota2 = Nota(2,"MockNota2","MockNota2Content",true)

class MockNotaDao: NotaDao {
    private val notas = MutableStateFlow(listOf(nota1, nota2))

    override suspend fun insertNota(nota: Nota): Long {
        var notaAgregar = nota
        nota.notaId = notas.value.last().notaId + 1
        notas.value = notas.value?.toMutableList()?.apply { add(nota) }!!
        return nota.notaId.toLong()
    }

    override suspend fun updateNota(nota: Nota) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNota(nota: Nota) {
        notas.value = notas.value?.toMutableList()?.apply { remove(nota) }!!
    }

    override fun getAllNotas(): Flow<List<Nota>> {
        return flowOf(notas.value)
    }

    override fun getNotaById(id: Int): Nota {
        TODO("Not yet implemented")
    }

    override fun getArchivedNotas(archived: Boolean): Flow<List<Nota>> {
        return flowOf(notas.value.filter { it.archived == archived })
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