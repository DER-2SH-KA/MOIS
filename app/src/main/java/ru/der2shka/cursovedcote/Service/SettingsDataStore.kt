package ru.der2shka.cursovedcote.Service

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.der2shka.cursovedcote.LANGUAGE_KEY
import ru.der2shka.cursovedcote.THEME_KEY
import ru.der2shka.cursovedcote.dataStore

class SettingsDataStore(
    private val context: Context
) {

    suspend fun saveLanguageData(value: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = value
        }
    }

    suspend fun saveAppThemeData(value: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = value
        }
    }

    val getLanguageData: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: "none"
        }

    val getAppThemeData: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: "none"
        }

}