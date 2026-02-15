package com.jayden.bluetoothalerts.app.service

import android.app.Notification
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication
import com.jayden.bluetoothalerts.app.receivers.BluetoothEventReceiver

class BluetoothAlertService : Service() {
    inner class LocalBinder : Binder() {
        fun getService(): BluetoothAlertService = this@BluetoothAlertService
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    val eventReceiver = BluetoothEventReceiver()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand(intent = $intent, flags = $flags, startId = $startId)")
        val notification: Notification = Notification.Builder(
            this,
            MainApplication.NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID
        )
            .setContentTitle(resources.getString(R.string.notification_foreground_service_title))
            .setContentText(resources.getString(R.string.notification_foreground_service_description))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setLocalOnly(true)
            .setShowWhen(false)
            .build()

        Log.i(TAG, "Service moving to foreground with id of: $FOREGROUND_ID")
        startForeground(FOREGROUND_ID, notification)
        Log.i(TAG, "Registering event receiver")
        registerReceiver(eventReceiver, IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED)
            addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        })
        return START_STICKY
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        Log.i(TAG, "Unregistering event receiver")
        unregisterReceiver(eventReceiver)
        super.onDestroy()
    }

    fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    companion object {
        private const val TAG = "BluetoothAlertService"
        const val FOREGROUND_ID = 131072
    }
}