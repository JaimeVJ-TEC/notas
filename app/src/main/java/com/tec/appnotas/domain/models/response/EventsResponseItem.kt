package com.tec.appnotas.domain.models.response

data class EventsResponseItem(
    val content: String,
    val created_at: String,
    val id: String,
    val title: String
)