package com.jayden.bluetoothalerts.app.receivers

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jayden.bluetoothalerts.app.service.BluetoothAlertService

class BluetoothEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                TODO("Not yet implemented")
            }
            else -> {}
        }
    }
}