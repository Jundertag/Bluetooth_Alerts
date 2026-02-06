package com.jayden.bluetoothalerts.app.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication

class BluetoothAlertService() : Service() {
    inner class LocalBinder : Binder() {
        fun getService(): BluetoothAlertService = this@BluetoothAlertService
    }

    private val binder = LocalBinder()

    private val appNotifyManager = (application as MainApplication).appNotificationManager

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(startId, notification)
        return START_STICKY
    }

    fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private val notification: Notification = Notification.Builder(this,
        MainApplication.NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID)
        .setContentTitle(resources.getString(R.string.notification_foreground_service_title))
        .setContentText(resources.getString(R.string.notification_foreground_service_description))
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setCategory(Notification.CATEGORY_SERVICE)
        .setLocalOnly(true)
        .setShowWhen(false)
        .build()

    companion object {

    }
}