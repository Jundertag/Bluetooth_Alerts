package com.jayden.bluetoothalerts.app.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.jayden.bluetoothalerts.R

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
            fun fromId(id: Int): BluetoothBondState? = lookup[id]
        }
    }

    enum class BluetoothDeviceClassMajor(val id: Int) {
        AUDIO_VIDEO(1024),
        COMPUTER(256),
        HEALTH(2304),
        IMAGING(1536),
        MISC(0),
        NETWORKING(768),
        PERIPHERAL(1280),
        PHONE(512),
        TOY(2048),
        UNCATEGORIZED(7936),
        WEARABLE(1792);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothDeviceClassMajor? = lookup[id]
        }
    }

    enum class BluetoothDeviceClassMinor(val id: Int) {
        AUDIO_VIDEO_CAMCORDER(1076),
        AUDIO_VIDEO_CAR_AUDIO(1056),
        AUDIO_VIDEO_HANDSFREE(1032),
        AUDIO_VIDEO_HEADPHONES(1048),
        AUDIO_VIDEO_HIFI_AUDIO(1064),
        AUDIO_VIDEO_LOUDSPEAKER(1044),
        AUDIO_VIDEO_MICROPHONE(1040),
        AUDIO_VIDEO_PORTABLE_AUDIO(1052),
        AUDIO_VIDEO_SET_TOP_BOX(1060),
        AUDIO_VIDEO_UNCATEGORIZED(1024),
        AUDIO_VIDEO_VCR(1068),
        AUDIO_VIDEO_VIDEO_CAMERA(1072),
        AUDIO_VIDEO_VIDEO_CONFERENCING(1088),
        AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER(1084),
        AUDIO_VIDEO_VIDEO_GAMING_TOY(1096),
        AUDIO_VIDEO_VIDEO_MONITOR(1080),
        AUDIO_VIDEO_WEARABLE_HEADSET(1028),
        COMPUTER_DESKTOP(260),
        COMPUTER_HANDHELD_PC_PDA(272),
        COMPUTER_LAPTOP(268),
        COMPUTER_PALM_SIZE_PC_PDA(276),
        COMPUTER_SERVER(264),
        COMPUTER_UNCATEGORIZED(256),
        COMPUTER_WEARABLE(280),
        HEALTH_BLOOD_PRESSURE(2308),
        HEALTH_DATA_DISPLAY(2332),
        HEALTH_GLUCOSE(2320),
        HEALTH_PULSE_OXIMETER(2324),
        HEALTH_PULSE_RATE(2328),
        HEALTH_THERMOMETER(2312),
        HEALTH_UNCATEGORIZED(2304),
        HEALTH_WEIGHING(2316),
        PERIPHERAL_KEYBOARD(1344),
        PERIPHERAL_KEYBOARD_POINTING(1472),
        PERIPHERAL_NON_KEYBOARD_NON_POINTING(1280),
        PERIPHERAL_POINTING(1408),
        PHONE_CELLULAR(516),
        PHONE_CORDLESS(520),
        PHONE_ISDN(532),
        PHONE_MODEM_OR_GATEWAY(528),
        PHONE_SMART(524),
        PHONE_UNCATEGORIZED(512),
        TOY_CONTROLLER(2064),
        TOY_DOLL_ACTION_FIGURE(2060),
        TOY_GAME(2068),
        TOY_ROBOT(2052),
        TOY_UNCATEGORIZED(2048),
        TOY_VEHICLE(2056),
        WEARABLE_GLASSES(1812),
        WEARABLE_HELMET(1808),
        WEARABLE_JACKET(1804),
        WEARABLE_PAGER(1800),
        WEARABLE_UNCATEGORIZED(1792),
        WEARABLE_WRIST_WATCH(1796);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothDeviceClassMinor? = lookup[id]
        }
    }

    enum class BluetoothDeviceClassService(val id: Int) {
        AUDIO(2097152),
        CAPTURE(524288),
        INFORMATION(8388608),
        LE_AUDIO(16384),
        LIMITED_DISCOVERABILITY(8192),
        NETWORKING(131072),
        OBJECT_TRANSFER(1048576),
        POSITIONING(65536),
        RENDER(262144),
        TELEPHONY(4194304);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothDeviceClassService? = lookup[id]
        }
    }

    enum class BluetoothDeviceEncryptionAlgorithm(val id: Int) {
        AES(2),
        E0(1),
        NONE(0),
        UNKNOWN(3);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothDeviceEncryptionAlgorithm? = lookup[id]
        }
    }

    enum class BluetoothBondLossReason(val id: Int) {
        BREDR_AUTH_FAILURE(1),
        BREDR_INCOMING_PAIRING(2),
        LE_ENCRYPT_FAILURE(3),
        LE_INCOMING_PAIRING(4),
        UNKNOWN(0);

        override fun toString(): String {
            val format = name.lowercase().replace('_', ' ')
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }

        companion object {
            private val lookup = entries.associateBy { it.id }
            fun fromId(id: Int): BluetoothBondLossReason? = lookup[id]
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
            AppNotificationRegistry.NOTIFICATION_STATE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setContentTitle(
                ctx.resources.getString(
                    R.string.notification_state_update_title
                )
            )
            setContentText(ctx.resources.getString(R.string.notification_state_update_desc,
                state.toString()
            ))
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothStateNotification)
    }
    fun showBluetoothDiscoveryStateNotification(ctx: Context, id: Int, state: BluetoothDiscoveryState) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothDiscoveryStateNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_DISCOVERY_STATE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setContentTitle(
                ctx.resources.getString(R.string.notification_discovery_state_update_title, state.toString())
            )
            setStyle(Notification.BigTextStyle())
            setContentText(ctx.resources.getString(R.string.notification_discovery_state_update_desc))
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothDiscoveryStateNotification)
    }
    fun showBluetoothConnectionStateNotification(ctx: Context, id: Int, state: BluetoothConnectionState, address: String?, deviceName: String?, alias: String?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothConnectionStateNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_CONNECTION_STATE_ALERTS_CHANNEL_ID
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
            AppNotificationRegistry.NOTIFICATION_LOCAL_NAME_CHANGE_ALERTS_CHANNEL_ID
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
            AppNotificationRegistry.NOTIFICATION_SCAN_MODE_CHANGE_ALERTS_CHANNEL_ID
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
    fun showBluetoothAclConnectedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceAlias: String?, deviceTransport: BluetoothTransport?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothAclConnectedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_ACL_CONNECTED_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setContentTitle(
                ctx.resources.getString(R.string.notification_acl_connected_title)
            )
            setStyle(Notification.BigTextStyle())
            setContentText(
                ctx.resources.getString(
                    R.string.notification_acl_connected_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceAlias ?: "<null>",
                    deviceTransport?.toString() ?: "<upgrade-android-version>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothAclConnectedNotification)
    }
    fun showBluetoothAclDisconnectedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceAlias: String?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothAclDisconnectedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_ACL_DISCONNECTED_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_acl_disconnected_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_acl_disconnected_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceAlias ?: "<null>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothAclDisconnectedNotification)
    }
    fun showBluetoothAclDisconnectRequestedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceAlias: String?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothAclDisconnectRequestedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_ACL_DISCONNECT_REQUESTED_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_acl_disconnect_requested_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_acl_disconnect_requested_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceAlias ?: "<null>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothAclDisconnectRequestedNotification)
    }
    fun showBluetoothAliasChangedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceAlias: String?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothAliasChangedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_ALIAS_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_alias_changed_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_alias_changed_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceAlias ?: "<null>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothAliasChangedNotification)
    }
    fun showBluetoothBondStateChangedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceBondState: BluetoothBondState) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothBondStateChangedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_BOND_STATE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_bond_state_changed_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_bond_state_changed_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceBondState.toString()
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothBondStateChangedNotification)
    }
    fun showBluetoothClassChangedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceClass: BluetoothDeviceClassMajor, deviceMinorClass: BluetoothDeviceClassMinor) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothClassChangedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_CLASS_CHANGE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_class_changed_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_class_changed_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceClass.toString(),
                    deviceMinorClass.toString()
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothClassChangedNotification)
    }

    /**
     * [keySize] parameter is assumed to be bits, android reports key size as bytes. Multiply by 8 to get bits from bytes
     */
    fun showBluetoothEncryptionChangedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, encryptionStatusCode: Int, encryptionStatusCodeMeaning: String?, encryptionEnabled: Boolean, keySize: Int, encryptionAlgorithm: BluetoothDeviceEncryptionAlgorithm) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothEncryptionChangedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_ENCRYPTION_CHANGE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_encryption_changed_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_encryption_changed_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    encryptionStatusCode,
                    encryptionStatusCodeMeaning ?: "not documented",
                    encryptionEnabled.toString(),
                    keySize,
                    encryptionAlgorithm.toString()
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothEncryptionChangedNotification)
    }
    fun showBluetoothDeviceFoundNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceMajorClass: BluetoothDeviceClassMajor, rssi: Short?, deviceCoordinatedMember: Boolean?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothDeviceFoundNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_DEVICE_FOUND_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_device_found_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_device_found_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceMajorClass.toString(),
                    rssi ?: "not available",
                    deviceCoordinatedMember?.toString() ?: "<upgrade-android-version>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothDeviceFoundNotification)
    }
    fun showBluetoothKeyMissingNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, keyMissingReason: BluetoothBondLossReason?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothKeyMissingNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_KEY_MISSING_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_key_missing_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_key_missing_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    keyMissingReason ?: "<upgrade-android-version>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothKeyMissingNotification)
    }
    fun showBluetoothNameChangedNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothNameChangedNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_NAME_CHANGE_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_name_changed_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_name_changed_desc,
                    deviceAddress,
                    deviceName ?: "<null>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothNameChangedNotification)
    }
    fun showBluetoothPairingRequestNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, devicePairingKey: String) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothPairingRequestNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_PAIRING_REQUEST_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_pairing_request_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_pairing_request_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    devicePairingKey
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothPairingRequestNotification)
    }
    fun showBluetoothUuidNotification(ctx: Context, id: Int, deviceAddress: String, deviceName: String?, deviceUuids: String?) {
        val notificationManager = getNotifyManager(ctx)
        val bluetoothUuidNotification = Notification.Builder(
            ctx,
            AppNotificationRegistry.NOTIFICATION_UUID_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setStyle(Notification.BigTextStyle())
            setContentTitle(
                ctx.resources.getString(R.string.notification_uuid_title)
            )
            setContentText(
                ctx.resources.getString(
                    R.string.notification_uuid_desc,
                    deviceAddress,
                    deviceName ?: "<null>",
                    deviceUuids ?: "<os-failure>"
                )
            )
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }.build()
        notificationManager.notify(id, bluetoothUuidNotification)
    }
}