package com.tec.appnotas.domain.repository

import android.util.Log
import com.tec.appnotas.domain.dao.ImagenDao
import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.datasource.RestDataSource
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.models.post.PostItem
import com.tec.appnotas.domain.models.response.NotasResponseItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotaRepository {
    suspend fun getNota(id: String) : Nota
    suspend fun postNota(nota: Nota) : NotasResponseItem
    suspend fun getLocalNotas(archived: Boolean): Flow<List<Nota>>
    suspend fun getNotaById(id: Int): Nota
    suspend fun insertLocalNota(nota: Nota) : Int
    suspend fun updateLocalNota(nota: Nota)
    suspend fun deleteLocalNota(nota: Nota)
}

class NotaRepositoryImp @Inject constructor(
    private val dataSource: RestDataSource,
    private val notaDao: NotaDao,
    private val imagenDao: ImagenDao
) : NotaRepository{

    override suspend fun getNota(id: String): Nota {
        val response = dataSource.getNotaById(id)
        return Nota(notaId = -1, content = response[0].content,title = response[0].title)
    }

    override suspend fun postNota(nota: Nota): NotasResponseItem {
        val response = dataSource.postNota(PostItem(nota.content,nota.title))

        Log.d("INSERTED",response[0].title)
        return response[0]
    }

    override suspend fun getLocalNotas(archived: Boolean): Flow<List<Nota>> {
        return notaDao.getArchivedNotas(archived)
    }

    override suspend fun getNotaById(id: Int): Nota {
        return notaDao.getNotaById(id)
    }

    override suspend fun insertLocalNota(nota: Nota): Int {
        return notaDao.insertNota(nota).toInt()
    }

    override suspend fun updateLocalNota(nota: Nota) {
        notaDao.updateNota(nota)
    }

    override suspend fun deleteLocalNota(nota: Nota) {
        notaDao.deleteNota(nota)
    }
}