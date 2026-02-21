package com.jayden.bluetoothalerts.app

import android.app.Application
import android.app.NotificationManager
import android.util.Log
import com.jayden.bluetoothalerts.app.notifications.AppNotificationInitializer
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModelFactory
import com.jayden.bluetoothalerts.data.repo.events.BluetoothEventsRepository
import com.jayden.bluetoothalerts.data.repo.settings.SettingsRepository
import com.jayden.bluetoothalerts.data.source.settings.SettingsDataStore

class MainApplication : Application() {
    private lateinit var notificationManager: NotificationManager
    lateinit var settingsDataStore: SettingsDataStore
    lateinit var settingsRepository: SettingsRepository
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var bluetoothEventsRepository: BluetoothEventsRepository

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        settingsDataStore = SettingsDataStore(applicationContext)
        settingsRepository = SettingsRepository(settingsDataStore)
        bluetoothEventsRepository = BluetoothEventsRepository(applicationContext)
        mainViewModelFactory = MainViewModelFactory(settingsRepository, bluetoothEventsRepository)
        AppNotificationInitializer.compareAndRemoveNotificationChannels(applicationContext)
        AppNotificationInitializer.compareAndRemoveNotificationGroups(applicationContext)
        AppNotificationInitializer.ensureNotificationGroups(applicationContext)
        AppNotificationInitializer.ensureNotificationChannels(applicationContext)
    }

    companion object {
        private const val TAG = "MainApplication"
    }
}