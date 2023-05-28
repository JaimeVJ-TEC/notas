package com.tec.appnotas.domain.repository

import android.util.Log
import com.tec.appnotas.domain.dao.EventoDao
import com.tec.appnotas.domain.datasource.RestDataSource
import com.tec.appnotas.domain.models.Event
import com.tec.appnotas.domain.models.Nota
import com.tec.appnotas.domain.models.post.PostItem
import com.tec.appnotas.domain.models.response.EventsResponseItem
import com.tec.appnotas.domain.models.response.NotasResponseItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EventRepository {
    suspend fun getEvent(id: String) : Event
    suspend fun postEvent(event: Event) : EventsResponseItem
    suspend fun getEventById(id: Int): Event
    suspend fun insertLocalEvent(event: Event) : Int
    suspend fun updateLocalEvent(event: Event)
    suspend fun deleteLocalEvent(event: Event)
}


//class EventRepositoryImp @Inject constructor(
//    private val eventoDao: EventoDao,
//    private val dataSource: RestDataSource,
//) : EventRepository{
//
//    override suspend fun getEvent(id: String): Event {
//        val response = dataSource.getNotaById("eq.$id")
//        Log.d("ID",id)
//        Log.d("RESPONSE",response[0].title)
//        return Event(idEvento = response[0].id, title = response[0].title)
//    }
//
//    override suspend fun postEvent(event: Event): EventsResponseItem {
//        val response = dataSource.postNota(PostItem(event.title,event.eventBody))
//        Log.d("INSERTED",response[0].title)
//        return response[0]
//    }
//
//    override suspend fun getEventById(id: Int): Event {
//        return eventoDao.getEventById(id)
//    }
//
//    override suspend fun insertLocalEvent(event: Event): Int {
//        return eventoDao.insertEvento(event).toInt()
//    }
//
//    override suspend fun updateLocalEvent(event: Event) {
//        eventoDao.updateEvento(event)
//    }
//
//    override suspend fun deleteLocalEvent(event: Event) {
//        eventoDao.deleteEvento(event)
//    }
//}