package com.jayden.bluetoothalerts.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayden.bluetoothalerts.data.repo.SettingsRepository
import com.jayden.bluetoothalerts.proto.MonitorMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingsRepo: SettingsRepository
) : ViewModel() {
    data class SettingsUiState(
        val monitorMode: MonitorMode
    )

    private val settingsState = settingsRepo.settingsFlow(viewModelScope).map {
        SettingsUiState(it.monitorMode)
    }

    val settingsMonitorMode = settingsState.map { it.monitorMode }

    fun updateMonitorMode(to: MonitorMode) {
        viewModelScope.launch {
            settingsRepo.updateSettings { it.toBuilder().setMonitorMode(to).build() }
        }
    }
}