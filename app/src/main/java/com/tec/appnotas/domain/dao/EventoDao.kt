package com.tec.appnotas.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.tec.appnotas.domain.models.Event

@Dao
interface EventoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvento(evento: Event): Long

    @Update
    suspend fun updateEvento(evento: Event)

    @Delete
    suspend fun deleteEvento(evento: Event)

    @Query("SELECT * FROM evento")
    fun getAllEventos(): Flow<List<Event>>

//    @Query("SELECT * FROM evento where IdEvento = :id")
//    fun getEventById(id: Int): Event
}

