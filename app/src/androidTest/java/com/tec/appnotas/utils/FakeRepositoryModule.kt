package com.tec.appnotas.utils

import com.tec.appnotas.domain.di.RepositoryModule
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.models.response.NotasResponseItem
import com.tec.appnotas.domain.repository.NotaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {

    @Provides
    @Singleton
    fun notasRepository(): NotaRepository = object : NotaRepository{

        private val notas = MutableStateFlow<List<Nota>>(listOf())

        override suspend fun getNota(id: String): Nota {
            return Nota(title= "GET", content = "GETCONTENT",archived = false)
        }

        override suspend fun postNota(nota: Nota): NotasResponseItem {
            return NotasResponseItem("RESPONSE","2023-06-01T21:11:45.034101+00:00","c52f6bd9-32d7-4880-a40f-e518eb34f584", "RESPONSE")
        }

        override fun getLocalNotas(archived: Boolean): Flow<List<Nota>> {
            return flowOf(notas.value)
        }

        override suspend fun getNotaById(id: Int): Nota {
            return notas.value.filter { it.notaId == id }.first()
        }

        override suspend fun insertLocalNota(nota: Nota): Int {
            var notaAgregar = nota
            nota.notaId = if(notas.value.isEmpty()) 1 else notas.value.last().notaId + 1
            notas.value = notas.value?.toMutableList()?.apply { add(nota) }!!
            return nota.notaId
        }

        override suspend fun updateLocalNota(nota: Nota) {
            var updatedNota = nota
            var listNota = Nota(-1)
            for(note in notas.value){
                if(note.notaId == updatedNota.notaId) {
                    listNota == note
                }
            }
            if(listNota.notaId != -1) {
                val list = notas.value.toMutableList()
                list[list.indexOf(listNota)] = updatedNota
                notas.value = list
            }
        }

        override suspend fun deleteLocalNota(nota: Nota) {
            notas.value = notas.value?.toMutableList()?.apply { remove(nota) }!!
        }

    }
}