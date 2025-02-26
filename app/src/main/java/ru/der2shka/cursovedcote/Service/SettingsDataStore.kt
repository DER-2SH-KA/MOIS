package ru.der2shka.cursovedcote.Service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by
preferencesDataStore(name = "settings")

val LANGUAGE_KEY= stringPreferencesKey("lang")
val THEME_KEY = booleanPreferencesKey("theme")

class SettingsDataStore(
    private val context: Context
) {

    suspend fun saveLanguageData(value: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = value
        }
    }

    suspend fun saveAppThemeData(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = value
        }
    }

    val getLanguageData: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: "none"
        }

    val getAppThemeData: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: true
        }

}