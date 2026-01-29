package com.jayden.bluetoothalerts.data.repo

import com.jayden.bluetoothalerts.data.source.SettingsDataStore
import com.jayden.bluetoothalerts.proto.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class SettingsRepository(
    private val dataStore: SettingsDataStore,
    private val coroutineScope: CoroutineScope
) {
    val settingsFlow = dataStore.settingsFlow.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5_000),
        Settings.getDefaultInstance()
    )

    suspend fun updateSettings(transform: ((Settings) -> Settings)) {
        dataStore.updateSettings(transform)
    }
}