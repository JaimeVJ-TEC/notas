package com.tec.appnotas.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Nota(
    @PrimaryKey(autoGenerate = true)
    val notaId: Int,
    val title: String = "",
    val content: String = "",
    val archived: Boolean = false
)