package com.jayden.bluetoothalerts.app.receivers

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BluetoothDeviceEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {

            }
            BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> {

            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {

            }
        }
    }
}