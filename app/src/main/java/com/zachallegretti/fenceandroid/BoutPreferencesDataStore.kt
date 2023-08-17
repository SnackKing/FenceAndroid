package com.zachallegretti.fenceandroid

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BoutPreferencesDataStore {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)



    companion object {
        const val STORE_NAME = "bout_preferences"

        const val BOUT_TYPE_KEY = "bout_ty[e"
    }


}