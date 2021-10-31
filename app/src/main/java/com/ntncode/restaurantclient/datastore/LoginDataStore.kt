package com.ntncode.restaurantclient.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginDataStore(private val context: Context) {

    companion object {

        val LOGIN_DATASTORE = "login_datastore"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LOGIN_DATASTORE)

        val STATE = stringPreferencesKey("STATE")
        val DATE = stringPreferencesKey("DATE")
        val TIME = stringPreferencesKey("TIME")
    }

    // ************************ SET METHODS ***********************************

    suspend fun setData(state: String, date: String, time: String) {
        context.dataStore.edit {
            it.clear()
            it[STATE] = state
            it[DATE] = date
            it[TIME] = time
        }
    }

    suspend fun setState(phone: String) {
        context.dataStore.edit {
            it[STATE] = phone
        }
    }

    suspend fun setLogOut() {
        context.dataStore.edit {
            it[STATE] = "denied"
        }
    }

    // ************************ GET METHODS ***********************************

    val getState: Flow<String?> = context.dataStore.data.map {
        it[STATE] ?: "denied"
    }

    val getDate: Flow<String?> = context.dataStore.data.map {
        it[DATE]
    }

    val getTime: Flow<String?> = context.dataStore.data.map {
        it[TIME]
    }

}