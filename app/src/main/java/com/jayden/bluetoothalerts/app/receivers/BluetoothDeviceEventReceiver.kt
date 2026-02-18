package com.jayden.bluetoothalerts.app.receivers

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.jayden.bluetoothalerts.app.notifications.AppNotificationManager

class BluetoothDeviceEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                val bluetoothDevice = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(
                        BluetoothDevice.EXTRA_DEVICE,
                        BluetoothDevice::class.java
                    )
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                }

                val bluetoothTransport = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getIntExtra(BluetoothDevice.EXTRA_TRANSPORT, BluetoothDevice.ERROR)
                } else null


            }
            BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> {

            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {

            }
            BluetoothDevice.ACTION_ALIAS_CHANGED -> {

            }
            BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {

            }
            BluetoothDevice.ACTION_CLASS_CHANGED -> {

            }
            BluetoothDevice.ACTION_ENCRYPTION_CHANGE -> {

            }
            BluetoothDevice.ACTION_FOUND -> {

            }
            BluetoothDevice.ACTION_KEY_MISSING -> {

            }
            BluetoothDevice.ACTION_NAME_CHANGED -> {

            }
            BluetoothDevice.ACTION_PAIRING_REQUEST -> {

            }
            BluetoothDevice.ACTION_UUID -> {

            }
        }
    }
}