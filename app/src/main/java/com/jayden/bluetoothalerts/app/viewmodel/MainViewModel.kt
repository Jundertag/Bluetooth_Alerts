package com.jayden.bluetoothalerts.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayden.bluetoothalerts.data.repo.settings.SettingsRepository
import com.jayden.bluetoothalerts.proto.MonitorMode
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingsRepo: SettingsRepository
) : ViewModel() {
    data class SettingsUiState(
        val monitorMode: MonitorMode,
        val foregroundServiceEnabled: Boolean
    )

    private val settingsState = settingsRepo.settingsFlow(viewModelScope).map {
        SettingsUiState(it.monitorMode, it.foregroundServiceEnabled)
    }

    val settingsMonitorMode = settingsState.map { it.monitorMode }
    val settingsForegroundServiceEnabled = settingsState.map { it.foregroundServiceEnabled }

    fun updateMonitorMode(to: MonitorMode) {
        Log.d(TAG, "updateMonitorMode(MonitorMode.${to.name})")
        viewModelScope.launch {
            settingsRepo.updateSettings { it.toBuilder().setMonitorMode(to).build() }
        }
    }

    fun updateForegroundServiceEnabled(to: Boolean) {
        Log.d(TAG, "updateForegroundServiceEnabled($to)")
        viewModelScope.launch {
            settingsRepo.updateSettings { it.toBuilder().setForegroundServiceEnabled(to).build() }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}