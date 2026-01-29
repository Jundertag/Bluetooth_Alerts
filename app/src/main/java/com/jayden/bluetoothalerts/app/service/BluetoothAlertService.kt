package com.jayden.bluetoothalerts.app.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication

class BluetoothAlertService() : Service() {
    data class ServiceState(
        val started: Boolean,
        val bounded: Boolean,
        val foregrounded: Boolean
    )
    inner class LocalBinder : Binder() {
        fun getService(): BluetoothAlertService = this@BluetoothAlertService
    }

    private val binder = LocalBinder()

    private var state: ServiceState = ServiceState(started = false, bounded = false, foregrounded = false)

    override fun onBind(intent: Intent): IBinder? {
        if (intent.action == ACTION_MANAGE) {
            state = state.copy(bounded = true)
            return binder
        } else {
            return null
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(startId, notification)
        state = state.copy(started = true, foregrounded = true)
        return START_STICKY
    }

    override fun onDestroy() {
        state = state.copy(started = false, bounded = false, foregrounded = false)
        super.onDestroy()
    }

    private val notification: Notification = Notification.Builder(this,NOTIFICATION_ID)
        .setContentTitle("Service is running")
        .setContentText("We are passively listening for events. We will notify you of all events you chose to be notified about!")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setLocalOnly(true)
        .setShowWhen(false)
        .setCategory(MainApplication.NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID)
        .build()

    fun getState() = this.state

    companion object {
        private const val NOTIFICATION_ID = "bluetooth-alert-service"
        const val ACTION_MANAGE = "com.jayden.intent.action.MANAGE"
    }
}