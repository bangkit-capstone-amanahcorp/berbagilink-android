package com.example.ptamanah.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user
        }
    }


    fun getSession(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveSessionTenant(user: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_TENANT] = user
        }
    }

    fun getSessionTenant(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_TENANT]
        }
    }


    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    fun getEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[EMAIL]
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val TOKEN_TENANT = stringPreferencesKey("tokenTenant")
        private val EMAIL = stringPreferencesKey("email")
    }
}