package com.jayden.bluetoothalerts.app.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication

class AppNotificationManager(
    private val ctx: Context
) {
    private val notificationManager = ctx.getSystemService(NotificationManager::class.java)

    enum class BluetoothState(val id: Int) {
        OFFLINE(10),
        TURNING_ON(11),
        ONLINE(12),
        TURNING_OFF(13);

        override fun toString(): String {
            val format = name.lowercase().replace('_',' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothState? = lookup[id]
        }
    }

    enum class BluetoothConnectionState(val id: Int) {
        DISCONNECTED(0),
        CONNECTING(1),
        CONNECTED(2),
        DISCONNECTING(3);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothConnectionState? = lookup[id]
        }
    }

    enum class BluetoothDiscoveryState {
        STARTED,
        FINISHED;

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }
    }

    fun hideNotification(id: Int) {
        notificationManager.cancel(id)
    }

    companion object {
        const val BLUETOOTH_STATE_NOTIFY_ID = 1
        const val BLUETOOTH_DISCOVERY_STATE_ID = 2
        const val BLUETOOTH_CONNECTION_NOTIFY_ID = 2048
        fun showBluetoothStateNotification(ctx: Context, id: Int, state: BluetoothState) {
            val notificationManager = ctx.getSystemService(NotificationManager::class.java)
            val bluetoothStateNotification = Notification.Builder(
                ctx,
                MainApplication.NOTIFICATION_STATE_ALERTS_CHANNEL_ID
            ).apply {
                setCategory(Notification.CATEGORY_STATUS)
                setContentTitle(
                    ctx.resources.getString(
                        R.string.notification_state_update_title,
                        state.toString()
                    )
                )
                setContentText(ctx.resources.getString(R.string.notification_state_update_desc))
                setSmallIcon(R.drawable.ic_launcher_foreground)
            }.build() // here because kotlin doesn't believe type changes from within the apply block.
            notificationManager.notify(id, bluetoothStateNotification)
        }
        fun showBluetoothDiscoveryStateNotification(ctx: Context, id: Int, state: BluetoothDiscoveryState) {
            val notificationManager = ctx.getSystemService(NotificationManager::class.java)
            val bluetoothDiscoveryStateNotification = Notification.Builder(
                ctx,
                MainApplication.NOTIFICATION_DISCOVERY_STATE_ALERTS_CHANNEL_ID
            ).apply {
                setCategory(Notification.CATEGORY_STATUS)
                setContentTitle(
                    ctx.resources.getString(R.string.notification_discovery_state_update_title, state.toString())
                )
                setContentText(ctx.resources.getString(R.string.notification_discovery_state_update_desc))
                setSmallIcon(R.drawable.ic_launcher_foreground)
            }.build()
            notificationManager.notify(id, bluetoothDiscoveryStateNotification)
        }
        fun showBluetoothConnectionStateNotification(ctx: Context, id: Int, state: BluetoothConnectionState, deviceName: String?, address: String?) {
            val notificationManager = ctx.getSystemService(NotificationManager::class.java)
            val bluetoothConnectionStateNotification = Notification.Builder(
                ctx,
                MainApplication.NOTIFICATION_CONNECTION_STATE_ALERTS_CHANNEL_ID
            ).apply {
                setCategory(Notification.CATEGORY_STATUS)
                setContentTitle(
                    ctx.resources.getString(
                        R.string.notification_connection_state_update_title,
                        state.toString()
                    )
                )
                setContentText(ctx.resources.getString(R.string.notification_connection_state_update_desc, deviceName ?: "<null>", address ?: "<null>"))
                setSmallIcon(R.drawable.ic_launcher_foreground)
            }.build()
            notificationManager.notify(id, bluetoothConnectionStateNotification)
        }
    }
}