package com.jayden.bluetoothalerts.app.receivers

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Build
import android.os.ParcelUuid

object IntentHelper {
    fun getBluetoothDevice(intent: Intent): BluetoothDevice? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return intent.getParcelableExtra(
                BluetoothDevice.EXTRA_DEVICE,
                BluetoothDevice::class.java
            )
        } else {
            @Suppress("DEPRECATION")
            return intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        }
    }

    fun getBluetoothDeviceUuids(intent: Intent): String? {
        val uuids = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID, ParcelUuid::class.java)
        } else {
            @Suppress("DEPRECATION","UNCHECKED_CAST")
            intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID) as? Array<ParcelUuid>
        }

        if (uuids == null) {
            return null
        }

        val builder = StringBuilder()
        uuids.forEach { uuid ->
            builder.appendLine(uuid.toString())
        }
        return builder.toString()
    }
}