package com.jayden.bluetoothalerts.app.services

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication

class BluetoothAlertService : Service() {
    inner class LocalBinder : Binder() {
        fun getService(): BluetoothAlertService = this@BluetoothAlertService
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return if (intent != null && intent.action == ACTION_START && intent.categories.contains(
                CATEGORY_START_SERVICE
            )
        ) {
            this.binder
        } else {
            null
        }
    }

    private val notification: Notification = Notification.Builder(this,NOTIFICATION_ID)
        .setContentTitle("Service is running")
        .setContentText("We are passively listening for events. We will notify you of all events you chose to be notified about!")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setLocalOnly(true)
        .setShowWhen(false)
        .setCategory(MainApplication.NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID)
        .build()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(startId, notification)
        return START_STICKY
    }

    companion object {
        private const val NOTIFICATION_ID = "bluetooth-alert-service"
        const val ACTION_START = "com.jayden.intent.action.START"
        const val CATEGORY_START_SERVICE = "com.jayden.intent.category.START_SERVICE"
    }
}