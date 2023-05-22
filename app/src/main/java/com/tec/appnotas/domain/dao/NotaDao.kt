package com.tec.appnotas.domain.dao

import androidx.room.*
import com.tec.appnotas.domain.models.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNota(nota: Nota): Long

    @Update
    suspend fun updateNota(nota: Nota)

    @Delete
    suspend fun deleteNota(nota: Nota)

    @Query("SELECT * FROM notas")
    fun getAllNotas(): Flow<List<Nota>>

    @Query("SELECT * FROM notas where notaId = :id")
    fun getNotaById(id: Int): Nota

    @Query("SELECT * FROM notas WHERE archived = :archived")
    fun getArchivedNotas(archived: Boolean): Flow<List<Nota>>
}