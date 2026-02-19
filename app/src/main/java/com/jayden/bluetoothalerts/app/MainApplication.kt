package com.jayden.bluetoothalerts.app

import android.app.Application
import android.app.NotificationManager
import android.util.Log
import com.jayden.bluetoothalerts.app.notifications.AppNotificationInitializer
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModelFactory
import com.jayden.bluetoothalerts.data.repo.SettingsRepository
import com.jayden.bluetoothalerts.data.source.SettingsDataStore

class MainApplication : Application() {
    private lateinit var notificationManager: NotificationManager
    lateinit var settingsDataStore: SettingsDataStore
    lateinit var settingsRepository: SettingsRepository
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        settingsDataStore = SettingsDataStore(applicationContext)
        settingsRepository = SettingsRepository(settingsDataStore)
        mainViewModelFactory = MainViewModelFactory(settingsRepository)
        AppNotificationInitializer.compareAndRemoveNotificationChannels(applicationContext)
        AppNotificationInitializer.compareAndRemoveNotificationGroups(applicationContext)
        AppNotificationInitializer.ensureNotificationGroups(applicationContext)
        AppNotificationInitializer.ensureNotificationChannels(applicationContext)
    }

    companion object {
        private const val TAG = "MainApplication"
    }
}