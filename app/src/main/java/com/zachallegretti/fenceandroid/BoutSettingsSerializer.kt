package com.zachallegretti.fenceandroid

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object BoutSettingsSerializer : Serializer<BoutSettings> {
    override val defaultValue: BoutSettings = BoutSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): BoutSettings {
        try {
            return BoutSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: BoutSettings,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.boutSettingsDataStore: DataStore<BoutSettings> by dataStore(
    fileName = "bout_settings.pb",
    serializer = BoutSettingsSerializer
)