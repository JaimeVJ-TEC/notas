package com.tec.appnotas.domain.dao

import androidx.room.*
import com.tec.appnotas.domain.models.Imagen
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagen(imagen: Imagen)

    @Update
    suspend fun updateImagen(imagen: Imagen)

    @Delete
    suspend fun deleteImagen(imagen: Imagen)

    @Query("SELECT * FROM imagenes")
    fun getAllImagenes(): Flow<List<Imagen>>

    @Query("SELECT * FROM imagenes WHERE nota_id = :notaId")
    fun getImagenesByNotaId(notaId: Int): Flow<List<Imagen>>
}