package com.tec.appnotas.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evento")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val idEvento: Int = 0,
    val title: String,
    val eventBody: String,
    val selectedDay: Int,
    val currentMonth: Int
)
