package com.jayden.bluetoothalerts.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jayden.bluetoothalerts.data.repo.events.BluetoothEventsRepository
import com.jayden.bluetoothalerts.data.repo.settings.SettingsRepository

class MainViewModelFactory(
    private val settingsRepository: SettingsRepository,
    private val bluetoothEventsRepository: BluetoothEventsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(settingsRepository, bluetoothEventsRepository) as T
    }
}