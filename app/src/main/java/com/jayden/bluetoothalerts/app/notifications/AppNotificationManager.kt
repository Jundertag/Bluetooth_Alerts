package com.jayden.bluetoothalerts.app.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication

object AppNotificationManager {
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

    enum class BluetoothScanMode(val id: Int) {
        NONE(20),
        CONNNECTABLE(21),
        CONNECTABLE_DISCOVERABLE(23);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothScanMode? = lookup[id]
        }
    }

    enum class BluetoothTransport(val id: Int) {
        BREDR(1),
        LE(2);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothTransport? = lookup[id]
        }
    }

    enum class BluetoothBondState(val id: Int) {
        NONE(10),
        BONDING(11),
        BONDED(12);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int):  BluetoothBondState? = lookup[id]
        }
    }


    const val BLUETOOTH_STATE_NOTIFY_ID = 1
    const val BLUETOOTH_DISCOVERY_STATE_ID = 2
    const val BLUETOOTH_LOCAL_NAME_NOTIFY_ID = 3
    const val BLUETOOTH_SCAN_MODE_NOTIFY_ID = 4
    const val BLUETOOTH_CONNECTION_NOTIFY_ID = 5
    const val BLUETOOTH_ACL_CONNECTED_NOTIFY_ID = 6
    const val BLUETOOTH_ACL_DISCONNECTED_NOTIFY_ID = 7
    const val BLUETOOTH_ACL_DISCONNECT_REQUESTED_NOTIFY_ID = 8
    const val BLUETOOTH_ALIAS_NOTIFY_ID = 9
    const val BLUETOOTH_BOND_STATE_NOTIFY_ID = 10
    const val BLUETOOTH_CLASS_NOTIFY_ID = 11
    const val BLUETOOTH_ENCRYPTION_NOTIFY_ID = 12
    const val BLUETOOTH_FOUND_NOTIFY_ID = 13
    const val BLUETOOTH_MISSING_KEY_NOTIFY_ID = 14
    const val BLUETOOTH_NAME_NOTIFY_ID = 15
    const val BLUETOOTH_PAIRING_REQUEST_NOTIFY_ID = 16
    const val BLUETOOTH_UUID_NOTIFY_ID = 17
    private fun getNotifyManager(ctx: Context): NotificationManager = ctx.getSystemService(NotificationManager::class.java)
    fun showBluetoothStateNotification(ctx: Context, id: Int, state: BluetoothState) {
        val notificationManager = getNotifyManager(ctx)
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
        }.build()
        notificationManager.notify(id, bluetoothStateNotification)
    }
    fun showBluetoothDiscoveryStateNotification(ctx: Context, id: Int, state: BluetoothDiscoveryState) {
        val notificationManager = getNotifyManager(ctx)
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
    fun showBluetoothConnectionStateNotification(ctx: Context, id: Int, state: BluetoothConnectionState, address: String?, deviceName: String?, alias: String?) {
        val notificationManager = getNotifyManager(ctx)
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
            setStyle(Notification.BigTextStyle())
            setContentText(
                ctx.resources.getString(
                    R.string.notification_connection_state_update_desc,
                    address ?: "<null>",
                    deviceName ?: "<null>",
                    alias ?: "<null>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothConnectionStateNotification)
    }
    fun showBluetoothLocalNameChangeNotification(ctx: Context, id: Int, newName: String?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothLocalNameChangeNotification = Notification.Builder(
            ctx,
            MainApplication.NOTIFICATION_LOCAL_NAME_CHANGE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setContentTitle(
                ctx.resources.getString(R.string.notification_local_name_update_title)
            )
            setContentText(
                ctx.resources.getString(R.string.notification_local_name_update_desc, newName ?: "<null>")
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothLocalNameChangeNotification)
    }
    fun showBluetoothScanModeChangeNotification(ctx: Context, id: Int, state: BluetoothScanMode) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothScanModeChangeNotification = Notification.Builder(
            ctx,
            MainApplication.NOTIFICATION_SCAN_MODE_CHANGE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setContentTitle(
                ctx.resources.getString(R.string.notification_scan_mode_update_title)
            )
            setContentText(
                ctx.resources.getString(R.string.notification_scan_mode_update_desc, state.toString())
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothScanModeChangeNotification)
    }
    fun showBluetoothAclConnectedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceAlias: String?, deviceTransport: BluetoothTransport) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothAclConnectedNotification = Notification.Builder(
            ctx,
            MainApplication.NOTIFICATION_ACL_CONNECTED_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
        }
    }

}