package com.jayden.bluetoothalerts.app.receivers

import android.Manifest
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.jayden.bluetoothalerts.app.notifications.AppNotificationManager
import com.jayden.bluetoothalerts.app.service.BluetoothAlertService
import kotlin.random.Random

class BluetoothEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.v(TAG, "received intent: $intent")
        when (intent.action) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                Log.d(TAG, "Received ACTION_STATE_CHANGED Event")
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                if (state == BluetoothAdapter.ERROR) {
                    Log.i(TAG, "Bluetooth state returned an error, ignoring")
                    return
                }

                Log.i(TAG, "Notifying user of Bluetooth State Change")
                AppNotificationManager.showBluetoothStateNotification(
                    context.applicationContext,
                    AppNotificationManager.BLUETOOTH_STATE_NOTIFY_ID,
                    AppNotificationManager.BluetoothState.fromId(state)!!
                )
            }
            BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED -> {
                Log.d(TAG, "Received ACTION_CONNECTION_STATE_CHANGED Event")
                val connectionState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.ERROR)
                if (connectionState == BluetoothAdapter.ERROR) {
                    Log.i(TAG, "Bluetooth connection state returned an errror, ignoring")
                    return
                }

                val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                }

                val deviceName: String? = if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    device?.name
                } else {
                    "<missing-permission BLUETOOTH_CONNECT>"
                }
                val address: String? = if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    device?.address
                } else {
                    "<missing-permission BLUETOOTH_CONNECT>"
                }

                Log.i(TAG, "Notifying user of Bluetooth Connection State Change")
                AppNotificationManager.showBluetoothConnectionStateNotification(
                    context.applicationContext,
                    AppNotificationManager.BLUETOOTH_CONNECTION_NOTIFY_ID + deviceName.hashCode(),
                    AppNotificationManager.BluetoothConnectionState.fromId(connectionState)!!,
                    deviceName,
                    address
                )
            }
            BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                Log.d(TAG, "Received ACTION_DISCOVERY_STARTED Event")

                Log.i(TAG, "Notifying user of Bluetooth Discovery State Change")
                AppNotificationManager.showBluetoothDiscoveryStateNotification(
                    context.applicationContext,
                    AppNotificationManager.BLUETOOTH_DISCOVERY_STATE_ID,
                    AppNotificationManager.BluetoothDiscoveryState.STARTED
                )
            }
            BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                Log.d(TAG, "Received ACTION_DISCOVERY_FINISHED Event")

                Log.i(TAG, "Notifying user of Bluetooth Discovery State Change")
                AppNotificationManager.showBluetoothDiscoveryStateNotification(
                    context.applicationContext,
                    AppNotificationManager.BLUETOOTH_DISCOVERY_STATE_ID,
                    AppNotificationManager.BluetoothDiscoveryState.FINISHED
                )
            }
            else -> {}
        }
    }

    companion object {
        private const val TAG = "BluetoothEventReceiver"
    }
}