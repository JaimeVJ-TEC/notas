package com.tec.appnotas.domain.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tec.appnotas.domain.dao.ImagenDao
import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.models.Imagen
import com.tec.appnotas.domain.models.Nota

@Database(entities = [Nota::class,Imagen::class], version = 1, exportSchema = false)
abstract class NotasDatabase : RoomDatabase() {
    abstract fun notaDao(): NotaDao
    abstract fun imagenDao(): ImagenDao
}