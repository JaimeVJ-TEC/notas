package com.tec.appnotas.domain.di

import android.content.Context
import androidx.room.Room
import com.tec.appnotas.domain.dao.ImagenDao
import com.tec.appnotas.domain.dao.NotaDao
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
class DatabaseModule{

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseURL() = "https://fxvoskxrheajdwjiuwoj.supabase.co/"

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl : String): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory((GsonConverterFactory.create()))
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): RestDataSource = retrofit.create(RestDataSource::class.java)

    @Provides
    fun provideNotaDao(notasDatabase: NotasDatabase): NotaDao{
        return notasDatabase.notaDao()
    }

    @Provides
    fun provideImagenDao(notasDatabase: NotasDatabase): ImagenDao{
        return notasDatabase.imagenDao()
    }

    @Provides
    @Singleton
    fun provideNotasDatabase(@ApplicationContext appContext: Context): NotasDatabase {
        return Room.databaseBuilder(
            appContext,
            NotasDatabase::class.java,
            "notas_database"
        ).build()
    }
}