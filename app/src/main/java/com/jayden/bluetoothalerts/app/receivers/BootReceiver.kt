package com.jayden.bluetoothalerts.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jayden.bluetoothalerts.app.service.BluetoothAlertService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                val serviceIntent = Intent(context, BluetoothAlertService::class.java)
                context.startService(serviceIntent)
            }
            else -> return
        }
    }
}