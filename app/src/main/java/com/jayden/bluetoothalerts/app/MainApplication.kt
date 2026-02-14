package com.jayden.bluetoothalerts.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.util.Log
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.notifications.AppNotificationManager
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModelFactory
import com.jayden.bluetoothalerts.data.repo.SettingsRepository
import com.jayden.bluetoothalerts.data.source.SettingsDataStore
import com.jayden.bluetoothalerts.data.source.settingsStore
import com.jayden.bluetoothalerts.proto.settings

class MainApplication : Application() {
    private lateinit var notificationManager: NotificationManager
    lateinit var settingsDataStore: SettingsDataStore
    lateinit var settingsRepository: SettingsRepository
    lateinit var mainViewModelFactory: MainViewModelFactory

    lateinit var appNotificationManager: AppNotificationManager

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        settingsDataStore = SettingsDataStore(applicationContext)
        settingsRepository = SettingsRepository(settingsDataStore)
        mainViewModelFactory = MainViewModelFactory(settingsRepository)
        appNotificationManager = AppNotificationManager(applicationContext)
        ensureNotificationGroups()
        ensureNotificationChannels()
    }

    private fun ensureNotificationGroups() {
        Log.d(TAG, "Creating notification groups")
        val notificationGroups = listOf(
            NotificationChannelGroup(
                NOTIFICATION_SERVICES_GROUP_ID,
                resources.getString(R.string.notification_group_services_name)
            ).apply {
                description = resources.getString(R.string.notification_group_service_description)
            },
            NotificationChannelGroup(
                NOTIFICATION_ALERTS_GROUP_ID,
                resources.getString(R.string.notification_group_alerts_name)
            ).apply {
                description = resources.getString(R.string.notification_group_alerts_description)
            }
        )

        notificationManager.createNotificationChannelGroups(notificationGroups)
    }

    private fun ensureNotificationChannels() {
        Log.d(TAG, "Creating notification channels")
        val notificationChannels = listOf(
            NotificationChannel(
                NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID,
                resources.getString(R.string.notification_category_foreground_service_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setShowBadge(false)
                group = NOTIFICATION_SERVICES_GROUP_ID
                description =
                    resources.getString(R.string.notification_category_foreground_service_description)
            },
            NotificationChannel(
                NOTIFICATION_STATE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_alert_state_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(true)
                group = NOTIFICATION_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_alert_state_description)
            },
            NotificationChannel(
                NOTIFICATION_CONNECTION_STATE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_alert_state_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_alert_connection_state_description)
            },
            NotificationChannel(
                NOTIFICATION_DISCOVERY_STATE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_alert_discovery_state_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_alert_discovery_state_description)
            }
        )

        notificationManager.createNotificationChannels(notificationChannels)
    }

    companion object {
        private const val TAG = "MainApplication"

        const val NOTIFICATION_SERVICES_GROUP_ID = "services-group"
        const val NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID = "bluetooth-alert-service-channel"

        const val NOTIFICATION_ALERTS_GROUP_ID = "alerts-group"
        const val NOTIFICATION_STATE_ALERTS_CHANNEL_ID = "bluetooth-state-alerts-channel"
        const val NOTIFICATION_CONNECTION_STATE_ALERTS_CHANNEL_ID = "bluetooth-connection-state-alerts-channel"
        const val NOTIFICATION_DISCOVERY_STATE_ALERTS_CHANNEL_ID = "bluetooth-discovery-state-alerts-channel"
    }
}