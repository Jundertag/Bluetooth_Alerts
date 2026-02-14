package com.jayden.bluetoothalerts.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.jayden.bluetoothalerts.proto.MonitorMode
import com.jayden.bluetoothalerts.proto.Settings
import com.jayden.bluetoothalerts.proto.copy
import kotlinx.coroutines.flow.Flow

val Context.settingsStore: DataStore<Settings> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer
)

class SettingsDataStore(val context: Context) {
    val settingsFlow: Flow<Settings> = context.settingsStore.data

    suspend fun updateSettings(transform: ((Settings) -> Settings)) {
        context.settingsStore.updateData(transform)
    }
}