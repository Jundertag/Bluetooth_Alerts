package com.jayden.bluetoothalerts.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModelFactory
import com.jayden.bluetoothalerts.data.repo.SettingsRepository
import com.jayden.bluetoothalerts.data.source.SettingsDataStore
import com.jayden.bluetoothalerts.data.source.settingsStore
import com.jayden.bluetoothalerts.proto.settings

class MainApplication : Application() {
    private val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
    val settingsDataStore = SettingsDataStore(applicationContext)
    val settingsRepository = SettingsRepository(settingsDataStore)
    val mainViewModelFactory = MainViewModelFactory(settingsRepository)

    override fun onCreate() {
        super.onCreate()
        ensureNotificationGroups()
        ensureNotificationChannels()
    }

    private fun ensureNotificationGroups() {
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
        val notificationChannels = listOf(
            NotificationChannel(
                NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID,
                resources.getString(R.string.notification_category_foreground_service_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setShowBadge(false)
                description =
                    resources.getString(R.string.notification_category_foreground_service_description)
            },
            NotificationChannel(
                NOTIFICATION_STATE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_alert_state_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(true)
                description =
                    resources.getString(R.string.notification_category_alert_state_description)
            }
        )

        notificationManager.createNotificationChannels(notificationChannels)
    }

    companion object {
        const val NOTIFICATION_SERVICES_GROUP_ID = "services-group"
        const val NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID = "bluetooth-alert-service-channel"

        const val NOTIFICATION_ALERTS_GROUP_ID = "alerts-group"
        const val NOTIFICATION_STATE_ALERTS_CHANNEL_ID = "bluetooth-state-alerts-channel"
    }
}