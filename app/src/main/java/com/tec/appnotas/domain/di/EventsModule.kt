package com.tec.appnotas.domain.di

import android.content.Context
import androidx.room.Room
import com.tec.appnotas.domain.dao.EventoDao
import com.tec.appnotas.domain.dao.ImagenDao
import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.datasource.EventoDatabase
import com.tec.appnotas.domain.datasource.NotasDatabase
import com.tec.appnotas.domain.datasource.RestDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class EventsModule{
    @Provides
    fun provideEventDao(eventoDatabase: EventoDatabase): EventoDao {
        return eventoDatabase.eventoDao
    }
    @Provides
    @Singleton
    fun provideEventsDatabase(@ApplicationContext appContext: Context): EventoDatabase {
        return Room.databaseBuilder(
            appContext,
            EventoDatabase::class.java,
            "eventos_database"
        ).build()
    }
}