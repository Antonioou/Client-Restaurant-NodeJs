package com.ntncode.restaurantclient.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ntncode.restaurantclient.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class   UserDataStore(private val context: Context) {

    companion object {

        val USER_DATASTORE = "user_datastore"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

        val FIRSTNAME = stringPreferencesKey("FIRSTNAME")
        val LASTNAME = stringPreferencesKey("LASTNAME")
        val PHONE_NUMBER = stringPreferencesKey("PHONE_NUMBER")
        val UID = stringPreferencesKey("UID")
    }

    // ************************ SET METHODS ***********************************

    suspend fun setData(list: UserData) {
        context.dataStore.edit {
            it.clear()
            it[FIRSTNAME] = list.firstName
            it[LASTNAME] = list.lastName
            it[PHONE_NUMBER] = list.phone
            it[UID] = list.uid
        }
    }

    suspend fun setPhoneNumber(phone: String) {
        context.dataStore.edit {
            it.clear()
            it[PHONE_NUMBER] = phone
        }
    }

    // ************************ GET METHODS ***********************************

    val getData: Flow<UserData> = context.dataStore.data.map {
        UserData(
            firstName = it[FIRSTNAME] ?: "",
            lastName = it[LASTNAME] ?: "",
            phone = it[PHONE_NUMBER] ?: "",
            uid = it[UID] ?: ""
        )
    }

    // FLOW LIVEDATA
    val getFirstName: Flow<String?> = context.dataStore.data.map {
        it[FIRSTNAME]
    }

    val getLastName: Flow<String?> = context.dataStore.data.map {
        it[LASTNAME]
    }

    val getPhoneNumber: Flow<String?> = context.dataStore.data.map {
        it[PHONE_NUMBER]
    }

    val getUIDName: Flow<String?> = context.dataStore.data.map {
        it[UID]
    }

    // DATA USUAL

    /*suspend fun getUserAllData() = context.dataStore.data.map {
        UserDataStore(
            firstName = it[FIRSTNAME] ?: "",
            lastName = it[LASTNAME] ?: "",
            phone = it[PHONE_NUMBER] ?: "",
            uid = it[UID] ?: ""
        )
    }
*/
    suspend fun getFirstNameLL(): String? {
        val preferences: Preferences = context.dataStore.data.first()
        return preferences[FIRSTNAME]
    }
/*
    suspend fun getPhoneNumber(): String? {
        val preferences: Preferences = context.dataStore.data.first()
        return preferences[PHONE_NUMBER]
    }*/

    /*
    *   NEW DATA MODEL
    */


    /*suspend fun putString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun getString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences: Preferences = context.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    val userAgeFlow: Flow<String?> = context.dataStore.data.map {
        it[FIRSTNAME]
    }*/

}