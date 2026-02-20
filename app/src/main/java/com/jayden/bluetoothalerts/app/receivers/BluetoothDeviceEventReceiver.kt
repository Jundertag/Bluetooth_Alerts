package com.jayden.bluetoothalerts.app.receivers

import android.Manifest
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.jayden.bluetoothalerts.app.notifications.AppNotificationManager
import com.jayden.bluetoothalerts.data.model.bluetooth.BluetoothHCIErrorCode

class BluetoothDeviceEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.v(TAG, "received intent: ${intent.action}")
        when (intent.action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val bluetoothTransport = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getIntExtra(BluetoothDevice.EXTRA_TRANSPORT, BluetoothDevice.ERROR)
                } else null
                if (bluetoothTransport == BluetoothDevice.ERROR) {
                    Log.i(TAG, "Bluetooth Device Transport returned an error, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? = if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    bluetoothDevice.name
                } else "<missing-permission BLUETOOTH_CONNECT>"
                val deviceAlias = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.alias
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                } else {
                    "<upgrade-android-version>"
                }

                val deviceTransport = if (bluetoothTransport != null) {
                    AppNotificationManager.BluetoothTransport.fromId(bluetoothTransport)
                } else null

                Log.i(TAG, "Notifying user of Bluetooth Acl Connected event.")
                AppNotificationManager.showBluetoothAclConnectedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_ACL_CONNECTED_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    deviceAlias,
                    deviceTransport
                )
            }
            BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? =
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.name
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                val deviceAlias = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.alias
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                } else {
                    "<upgrade-android-version>"
                }

                Log.i(TAG, "Notifying user of Bluetooth Acl Disconnect Requested event.")
                AppNotificationManager.showBluetoothAclDisconnectRequestedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_ACL_DISCONNECT_REQUESTED_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    deviceAlias
                )
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? =
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.name
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                val deviceAlias = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.alias
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                } else {
                    "<upgrade-android-version>"
                }

                Log.i(TAG, "Notifying user of Bluetooth Acl Disconnected event.")
                AppNotificationManager.showBluetoothAclDisconnectedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_ACL_DISCONNECTED_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    deviceAlias
                )
            }
            BluetoothDevice.ACTION_ALIAS_CHANGED -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? =
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.name
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                val deviceAlias = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.alias
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                } else {
                    "<upgrade-android-version>"
                }

                Log.i(TAG, "Notifying user of Bluetooth Device Alias Changed event.")
                AppNotificationManager.showBluetoothAliasChangedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_ALIAS_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    deviceAlias
                )
            }
            BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val bluetoothBondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR)
                if (bluetoothBondState == BluetoothDevice.ERROR) {
                    Log.i(TAG, "Bluetooth Bond State returned an error, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? =
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.name
                    } else "<missing-permission BLUETOOTH_CONNECT>"
                val deviceBondState = AppNotificationManager.BluetoothBondState.fromId(bluetoothBondState)!!

                Log.i(TAG, "Notifying user of Bluetooth Device Bond State Changed event.")
                AppNotificationManager.showBluetoothBondStateChangedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_BOND_STATE_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    deviceBondState
                )
            }
            BluetoothDevice.ACTION_CLASS_CHANGED -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val bluetoothClass = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_CLASS, BluetoothClass::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<BluetoothClass>(BluetoothDevice.EXTRA_CLASS)
                }

                if (bluetoothClass == null) {
                    Log.i(TAG, "received null device class, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? =
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.name
                    } else "<missing-permission BLUETOOTH_CONNECT>"

                Log.i(TAG, "Notifying user of Bluetooth Device Class Changed event.")
                AppNotificationManager.showBluetoothClassChangedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_CLASS_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    AppNotificationManager.BluetoothDeviceClassMajor.fromId(bluetoothClass.majorDeviceClass)!!,
                    AppNotificationManager.BluetoothDeviceClassMinor.fromId(bluetoothClass.deviceClass)!!
                )

            }
            BluetoothDevice.ACTION_ENCRYPTION_CHANGE -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val encryptionStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                    intent.getIntExtra(BluetoothDevice.EXTRA_ENCRYPTION_STATUS, BluetoothDevice.ERROR)
                } else null
                if (encryptionStatus == BluetoothDevice.ERROR) {
                    Log.i(TAG, "Bluetooth Encryption Status returned an error, ignoring")
                    return
                }
                if (encryptionStatus == null) {
                    Log.i(TAG, "Bluetooth Encryption Status is null, ignoring")
                    return
                }

                val encryptionEnabled = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                    intent.getBooleanExtra(BluetoothDevice.EXTRA_ENCRYPTION_ENABLED, false)
                } else null

                val keySize = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                    intent.getIntExtra(BluetoothDevice.EXTRA_KEY_SIZE, BluetoothDevice.ERROR)
                } else null
                if (keySize == BluetoothDevice.ERROR) {
                    Log.i(TAG, "Bluetooth Key Size returned an error, ignoring")
                    return
                }
                if (keySize == null) {
                    Log.i(TAG, "Bluetooth Key Size is null, ignoring")
                    return
                }

                val encryptionAlgorithm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                        intent.getIntExtra(BluetoothDevice.EXTRA_ENCRYPTION_ALGORITHM, BluetoothDevice.ERROR)
                    } else null
                if (encryptionAlgorithm == BluetoothDevice.ERROR) {
                    Log.i(TAG, "Bluetooth Encryption Algorithm returned an error, ignoring")
                    return
                }
                if (encryptionAlgorithm == null) {
                    Log.i(TAG, "Bluetooth Encryption Algorithm is null, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? =
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.name
                    } else "<missing-permission BLUETOOTH_CONNECT>"

                Log.i(TAG, "Notifying user of Bluetooth Encryption Changed event.")
                AppNotificationManager.showBluetoothEncryptionChangedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_ENCRYPTION_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    encryptionStatus,
                    BluetoothHCIErrorCode.lookupCode(encryptionStatus),
                    encryptionEnabled == true,
                    keySize * 8,
                    AppNotificationManager.BluetoothDeviceEncryptionAlgorithm.fromId(encryptionAlgorithm)!!
                )
            }
            BluetoothDevice.ACTION_FOUND -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val bluetoothClass = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(
                        BluetoothDevice.EXTRA_CLASS,
                        BluetoothClass::class.java
                    )
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<BluetoothClass>(BluetoothDevice.EXTRA_CLASS)
                }

                if (bluetoothClass == null) {
                    Log.i(TAG, "received null device class, ignoring")
                    return
                }

                val bluetoothRssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)

                val deviceCoordinatedMember = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getBooleanExtra(BluetoothDevice.EXTRA_IS_COORDINATED_SET_MEMBER, false)
                } else {
                    null
                }

                val deviceRssi = if (bluetoothRssi != Short.MIN_VALUE) {
                    bluetoothRssi
                } else null

                val deviceAddress = bluetoothDevice.address
                val deviceName: String? =
                    if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothDevice.name
                    } else "<missing-permission BLUETOOTH_CONNECT>"

                Log.i(TAG, "Notifying user of Bluetooth Device Found event.")
                AppNotificationManager.showBluetoothDeviceFoundNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_FOUND_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    AppNotificationManager.BluetoothDeviceClassMajor.fromId(bluetoothClass.majorDeviceClass)!!,
                    deviceRssi,
                    deviceCoordinatedMember
                )
            }
            BluetoothDevice.ACTION_KEY_MISSING -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val bluetoothBondLossReason = if (Build.VERSION.SDK_INT_FULL >= Build.VERSION_CODES_FULL.BAKLAVA_1) {
                    intent.getIntExtra(BluetoothDevice.EXTRA_BOND_LOSS_REASON, BluetoothDevice.ERROR)
                } else null

                if (bluetoothBondLossReason == BluetoothDevice.ERROR) {
                    Log.i(TAG, "Bluetooth Bond Loss Reason returned an error, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName = bluetoothDevice.name

                Log.i(TAG, "Notifying user of Bluetooth Device Key Missing event.")
                AppNotificationManager.showBluetoothKeyMissingNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_MISSING_KEY_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    bluetoothBondLossReason?.let { AppNotificationManager.BluetoothBondLossReason.fromId(it) }
                )
            }
            BluetoothDevice.ACTION_NAME_CHANGED -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName = bluetoothDevice.name

                Log.i(TAG, "Notifying user of Bluetooth Device Name Changed event.")
                AppNotificationManager.showBluetoothNameChangedNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_NAME_NOTIFY_ID,
                    deviceAddress,
                    deviceName
                )
            }
            BluetoothDevice.ACTION_PAIRING_REQUEST -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val bluetoothPairingKey = intent.getStringExtra(BluetoothDevice.EXTRA_PAIRING_KEY)
                if (bluetoothPairingKey == null) {
                    Log.i(TAG, "received null pairing key, ignoring")
                    return
                }

                val deviceAddress = bluetoothDevice.address
                val deviceName = bluetoothDevice.name

                Log.i(TAG, "Notifying user of Bluetooth Device Pairing Request event.")
                AppNotificationManager.showBluetoothPairingRequestNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_PAIRING_REQUEST_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    bluetoothPairingKey
                )
            }
            BluetoothDevice.ACTION_UUID -> {
                val bluetoothDevice = IntentHelper.getBluetoothDevice(intent)

                if (bluetoothDevice == null) {
                    Log.i(TAG, "received null device, ignoring")
                    return
                }

                val bluetoothDeviceUuids = IntentHelper.getBluetoothDeviceUuids(intent)

                val deviceAddress = bluetoothDevice.address
                val deviceName = bluetoothDevice.name

                Log.i(TAG, "Notifying user of Bluetooth Device UUID event.")
                AppNotificationManager.showBluetoothUuidNotification(
                    context,
                    AppNotificationManager.BLUETOOTH_UUID_NOTIFY_ID,
                    deviceAddress,
                    deviceName,
                    bluetoothDeviceUuids
                )
            }
            else -> {}
        }
    }

    companion object {
        private const val TAG = "BluetoothDeviceEventReceiver"
    }
}