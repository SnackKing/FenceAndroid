package com.zachallegretti.fenceandroid

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BoutPreferencesDataStore(val context: Context) {

    val boutSettingsFlow: Flow<BoutSettings> = context.boutSettingsDataStore.data

    suspend fun updateBoutMode(boutMode: Bout.BoutType ) {
        context.boutSettingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setBoutSettingsKey(boutMode.value)
                .build()
        }
    }

    suspend fun updateWeapon(weapon: Bout.WeaponType) {
        context.boutSettingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setWeaponKey(weapon.value)
                .build()
        }
    }

    suspend fun updatePracticeScore(scoreCap: Int) {
        context.boutSettingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setPracticeScore(scoreCap)
                .build()
        }
    }

    suspend fun setTimerTapModeEnabled(enabled: Boolean) {
        context.boutSettingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setTimerTapModeEnabled(enabled)
                .build()
        }
    }

    suspend fun setTimerBuzzEnabled(enabled: Boolean) {
        context.boutSettingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setTimerBuzzEnabled(enabled)
                .build()
        }
    }

    suspend fun setTimerSoundEnabled(enabled: Boolean) {
        context.boutSettingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setTimerSoundEnabled(enabled)
                .build()
        }
    }



    companion object {
        const val STORE_NAME = "bout_preferences"

        const val BOUT_TYPE_KEY = "bout_ty[e"
    }


}