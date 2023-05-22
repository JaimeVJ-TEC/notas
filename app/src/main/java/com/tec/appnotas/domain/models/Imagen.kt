package com.tec.appnotas.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "imagenes",
foreignKeys = [ForeignKey(
    entity = Nota::class,
    parentColumns = ["notaId"],
    childColumns = ["nota_id"],
    onDelete = ForeignKey.CASCADE
)]
)
data class Imagen(
    @PrimaryKey(autoGenerate = true)
    val imagenId: Int,
    val path: String = "",
    val nota_id: Int
)