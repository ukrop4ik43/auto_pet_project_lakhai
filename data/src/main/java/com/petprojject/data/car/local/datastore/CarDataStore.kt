package com.petprojject.data.car.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CarDataStore(private val context: Context) {
    val IS_INSTRUCTIONS_OPENED = booleanPreferencesKey("is_instructions_opened")
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    suspend fun getIsInstructionsOpened(): Boolean {
        return context.dataStore.data.first()[IS_INSTRUCTIONS_OPENED] ?: false
    }

    suspend fun setInstructionsOpenedToTrue() {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[IS_INSTRUCTIONS_OPENED] = true
            }
        }
    }
}