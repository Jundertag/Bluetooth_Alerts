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
            },
            NotificationChannelGroup(
                NOTIFICATION_DEVICE_ALERTS_GROUP_ID,
                resources.getString(R.string.notification_group_device_alerts_name)
            ).apply {
                description = resources.getString(R.string.notification_group_device_alerts_description)
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
            },
            NotificationChannel(
                NOTIFICATION_LOCAL_NAME_CHANGE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_alert_local_name_change_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_alert_local_name_change_description)
            },
            NotificationChannel(
                NOTIFICATION_SCAN_MODE_CHANGE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_alert_scan_mode_change_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_alert_scan_mode_change_description)
            },
            NotificationChannel(
                NOTIFICATION_ACL_CONNECTED_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_acl_connected_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_acl_connected_description)
            },
            NotificationChannel(
                NOTIFICATION_ACL_DISCONNECTED_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_acl_disconnected_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_acl_disconnected_description)
            },
            NotificationChannel(
                NOTIFICATION_ACL_DISCONNECT_REQUESTED_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_acl_disconnect_request_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_acl_disconnect_request_description)
            },
            NotificationChannel(
                NOTIFICATION_ALIAS_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_alias_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_alias_changed_description)
            },
            NotificationChannel(
                NOTIFICATION_BOND_STATE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_bond_state_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_bond_state_changed_description)
            },
            NotificationChannel(
                NOTIFICATION_CLASS_CHANGE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_class_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_class_changed_description)
            },
            NotificationChannel(
                NOTIFICATION_ENCRYPTION_CHANGE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_encryption_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_class_changed_description)
            },
            NotificationChannel(
                NOTIFICATION_DEVICE_FOUND_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_found_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_found_description)
            },
            NotificationChannel(
                NOTIFICATION_KEY_MISSING_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_key_missing_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_key_missing_description)
            },
            NotificationChannel(
                NOTIFICATION_NAME_CHANGE_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_name_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_name_changed_description)
            },
            NotificationChannel(
                NOTIFICATION_PAIRING_REQUEST_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_pairing_request_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_pairing_request_description)
            },
            NotificationChannel(
                NOTIFICATION_UUID_ALERTS_CHANNEL_ID,
                resources.getString(R.string.notification_category_device_uuid_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description = resources.getString(R.string.notification_category_device_uuid_description)
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
        const val NOTIFICATION_LOCAL_NAME_CHANGE_ALERTS_CHANNEL_ID = "bluetooth-local-name-change-alerts-channel"
        const val NOTIFICATION_SCAN_MODE_CHANGE_ALERTS_CHANNEL_ID = "bluetooth-scan-mode-change-alerts-channel"

        const val NOTIFICATION_DEVICE_ALERTS_GROUP_ID = "device-alerts-group"
        const val NOTIFICATION_ACL_CONNECTED_ALERTS_CHANNEL_ID = "bluetooth-acl-connected-alerts-channel"
        const val NOTIFICATION_ACL_DISCONNECTED_ALERTS_CHANNEL_ID = "bluetooth-acl-disconnected-alerts-channel"
        const val NOTIFICATION_ACL_DISCONNECT_REQUESTED_CHANNEL_ID = "bluetooth-acl-disonnect-requested-alerts-channel"
        const val NOTIFICATION_ALIAS_ALERTS_CHANNEL_ID = "bluetooth-alias-alerts-channel"
        const val NOTIFICATION_BOND_STATE_ALERTS_CHANNEL_ID = "bluetooth-bond-state-alerts-channel"
        const val NOTIFICATION_CLASS_CHANGE_ALERTS_CHANNEL_ID = "bluetooth-class-change-alerts-channel"
        const val NOTIFICATION_ENCRYPTION_CHANGE_ALERTS_CHANNEL_ID = "bluetooth-encryption-change-alerts-channel"
        const val NOTIFICATION_DEVICE_FOUND_ALERTS_CHANNEL_ID = "bluetooth-device-found-alerts-channel"
        const val NOTIFICATION_KEY_MISSING_ALERTS_CHANNEL_ID = "bluetooth-key-missing-alerts-channel"
        const val NOTIFICATION_NAME_CHANGE_ALERTS_CHANNEL_ID = "bluetooth-name-change-alerts-channel"
        const val NOTIFICATION_PAIRING_REQUEST_ALERTS_CHANNEL_ID = "bluetooth-pairing-request-alerts-channel"
        const val NOTIFICATION_UUID_ALERTS_CHANNEL_ID = "bluetooth-uuid-alerts-channel"
    }
}