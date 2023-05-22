package com.tec.appnotas.domain.di

import android.content.Context
import androidx.room.Room
import com.tec.appnotas.domain.dao.ImagenDao
import com.tec.appnotas.domain.dao.NotaDao
import com.tec.appnotas.domain.datasource.NotasDatabase
import com.tec.appnotas.domain.datasource.RestDataSource
import com.tec.appnotas.domain.repository.NotaRepository
import com.tec.appnotas.domain.repository.NotaRepositoryImp
import dagger.Binds
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
abstract class RepositoryModule{

    @Singleton
    @Binds
    abstract fun notasRepository(repo: NotaRepositoryImp): NotaRepository
}