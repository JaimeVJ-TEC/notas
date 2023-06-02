package com.tec.appnotas.domain.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context){
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val DESCRIPTION_KEY = booleanPreferencesKey("show_description")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    val getLanguageValue: Flow<String> = context.dataStore.data.map {
        it[LANGUAGE_KEY] ?: "es"
    }

    suspend fun saveLanguageValue(language: String){
        context.dataStore.edit {
            it[LANGUAGE_KEY] = language
        }
    }

    val getDarkModeValue: Flow<Boolean> = context.dataStore.data.map{
        it[DARK_MODE_KEY] ?: false
    }

    suspend fun saveDarkModeValue(darkMode: Boolean){
        context.dataStore.edit{
            it[DARK_MODE_KEY] = darkMode
        }
    }

    val getDescriptionValue: Flow<Boolean> = context.dataStore.data.map {
        it[DESCRIPTION_KEY] ?: false
    }

    suspend fun saveDescriptionValue(description: Boolean){
        context.dataStore.edit {
            it[DESCRIPTION_KEY] = description
        }
    }
}