package com.augieafr.reqresapp.data.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val USER_PREFERENCES_NAME = "user_preferences"
class UserPreference @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferenceKeys {
        val TOKEN_KEY = stringPreferencesKey("user_token")
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data.map {
            it[PreferenceKeys.TOKEN_KEY] ?: ""
        }
    }

    suspend fun setUserToken(token: String) {
        dataStore.edit {
            val currentToken = it[PreferenceKeys.TOKEN_KEY]
            if (token == currentToken) return@edit
            it[PreferenceKeys.TOKEN_KEY] = token
        }
    }
}