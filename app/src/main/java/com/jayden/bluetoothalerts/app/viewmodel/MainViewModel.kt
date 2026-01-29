package com.jayden.bluetoothalerts.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayden.bluetoothalerts.data.repo.SettingsRepository
import com.jayden.bluetoothalerts.proto.MonitorMode
import com.jayden.bluetoothalerts.proto.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: SettingsRepository
) : ViewModel() {
    data class SettingsUiState(
        val monitorMode: MonitorMode
    )

    val settingsState = repo.settingsFlow.map {
        SettingsUiState(it.monitorMode)
    }

    fun updateMonitorMode(to: MonitorMode) {
        viewModelScope.launch {
            repo.updateSettings { it.toBuilder().setMonitorMode(to).build() }
        }
    }
}