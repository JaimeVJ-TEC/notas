package com.tec.appnotas.domain.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tec.appnotas.domain.dao.EventoDao
import com.tec.appnotas.domain.models.Event

@Database(entities = [Event::class], version = 1)
abstract class EventoDatabase : RoomDatabase() {
    abstract val dao : EventoDao
}

