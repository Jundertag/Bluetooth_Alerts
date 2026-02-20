package com.jayden.bluetoothalerts.app.notifications

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.jayden.bluetoothalerts.R

object AppNotificationInitializer {
    fun compareAndRemoveNotificationChannels(ctx: Context) {
        val notificationManager = ctx.getSystemService(NotificationManager::class.java)
        Log.i(TAG, "comparing current channels with owned channels")
        notificationManager.notificationChannels.forEach { channel ->
            if (AppNotificationRegistry.channelIdList.contains(channel.id)) {
                Log.v(TAG, "came across known owned channel of id: ${channel.id}")
                return@forEach
            }
            Log.d(TAG, "found unknown channel owned by this app, deleting channel: ${channel.id}")
            notificationManager.deleteNotificationChannel(channel.id)
        }
    }
    fun compareAndRemoveNotificationGroups(ctx: Context) {
        val notificationManager = ctx.getSystemService(NotificationManager::class.java)
        notificationManager.notificationChannelGroups.forEach { group ->
            if (AppNotificationRegistry.groupIdList.contains(group.id)) {
                Log.v(TAG, "came across known owned group of id: ${group.id}")
                return@forEach
            }
            Log.d(TAG, "found unknown group owned by this app, deleting group: ${group.id}")
            notificationManager.deleteNotificationChannel(group.id)
        }
    }
    fun ensureNotificationGroups(ctx: Context) {
        val notificationManager = ctx.getSystemService(NotificationManager::class.java)
        Log.d(TAG, "Creating notification groups")
        val notificationGroups = listOf(
            NotificationChannelGroup(
                AppNotificationRegistry.NOTIFICATION_SERVICES_GROUP_ID,
                ctx.resources.getString(R.string.notification_group_services_name)
            ).apply {
                description = ctx.resources.getString(R.string.notification_group_service_description)
            },
            NotificationChannelGroup(
                AppNotificationRegistry.NOTIFICATION_ALERTS_GROUP_ID,
                ctx.resources.getString(R.string.notification_group_alerts_name)
            ).apply {
                description = ctx.resources.getString(R.string.notification_group_alerts_description)
            },
            NotificationChannelGroup(
                AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID,
                ctx.resources.getString(R.string.notification_group_device_alerts_name)
            ).apply {
                description =
                    ctx.resources.getString(R.string.notification_group_device_alerts_description)
            }
        )

        notificationManager.createNotificationChannelGroups(notificationGroups)
    }

    fun ensureNotificationChannels(ctx: Context) {
        val notificationManager = ctx.getSystemService(NotificationManager::class.java)
        Log.d(TAG, "Creating notification channels")
        val notificationChannels = listOf(
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_foreground_service_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setShowBadge(false)
                group = AppNotificationRegistry.NOTIFICATION_SERVICES_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_foreground_service_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_STATE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_alert_state_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(true)
                group = AppNotificationRegistry.NOTIFICATION_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_alert_state_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_CONNECTION_STATE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_alert_state_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_alert_connection_state_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_DISCOVERY_STATE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_alert_discovery_state_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_alert_discovery_state_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_LOCAL_NAME_CHANGE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_alert_local_name_change_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_alert_local_name_change_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_SCAN_MODE_CHANGE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_alert_scan_mode_change_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_alert_scan_mode_change_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_ACL_CONNECTED_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_acl_connected_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_acl_connected_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_ACL_DISCONNECTED_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_acl_disconnected_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_acl_disconnected_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_ACL_DISCONNECT_REQUESTED_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_acl_disconnect_request_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_acl_disconnect_request_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_ALIAS_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_alias_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_alias_changed_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_BOND_STATE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_bond_state_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_bond_state_changed_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_CLASS_CHANGE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_class_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_class_changed_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_ENCRYPTION_CHANGE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_encryption_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_encryption_changed_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_DEVICE_FOUND_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_found_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_found_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_KEY_MISSING_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_key_missing_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_key_missing_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_NAME_CHANGE_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_name_changed_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_name_changed_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_PAIRING_REQUEST_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_pairing_request_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_pairing_request_description)
            },
            NotificationChannel(
                AppNotificationRegistry.NOTIFICATION_UUID_ALERTS_CHANNEL_ID,
                ctx.resources.getString(R.string.notification_category_device_uuid_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = AppNotificationRegistry.NOTIFICATION_DEVICE_ALERTS_GROUP_ID
                description =
                    ctx.resources.getString(R.string.notification_category_device_uuid_description)
            }
        )

        notificationManager.createNotificationChannels(notificationChannels)
    }

    private const val TAG = "AppNotificationInitializer"
}