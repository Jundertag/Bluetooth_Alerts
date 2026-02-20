package com.jayden.bluetoothalerts.data.repo.settings

import com.jayden.bluetoothalerts.data.source.settings.SettingsDataStore
import com.jayden.bluetoothalerts.proto.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

class SettingsRepository(
    private val dataStore: SettingsDataStore
) {
    fun settingsFlow(scope: CoroutineScope) = dataStore.settingsFlow.distinctUntilChanged().stateIn(
        scope,
        SharingStarted.Companion.WhileSubscribed(5_000),
        Settings.getDefaultInstance()
    )

    suspend fun updateSettings(transform: ((Settings) -> Settings)) {
        dataStore.updateSettings(transform)
    }
}