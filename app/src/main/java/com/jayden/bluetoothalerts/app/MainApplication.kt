package com.jayden.bluetoothalerts.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModelFactory

class MainApplication : Application() {
    private val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
    val mainViewModelFactory = MainViewModelFactory()

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
            })

        notificationManager.createNotificationChannelGroups(notificationGroups)
    }

    private fun ensureNotificationChannels() {
        val notificationChannels = listOf(NotificationChannel(
            NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID,
            resources.getString(R.string.notification_category_foreground_service_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            setShowBadge(false)
            description = resources.getString(R.string.notification_category_foreground_service_description)
        })

        notificationManager.createNotificationChannels(notificationChannels)
    }

    companion object {
        const val NOTIFICATION_SERVICES_GROUP_ID = "services-group"
        const val NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID = "bluetooth-alert-service-channel"
    }
}