package com.jayden.bluetoothalerts.app.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication

class AppNotificationManager(
    private val ctx: Context
) {
    private val notificationManager = ctx.getSystemService(NotificationManager::class.java)

    enum class BluetoothState {
        OFFLINE,
        ONLINE;

        override fun toString(): String {
            val format = name.lowercase()
            val upperChar = format[0].uppercase()
            return format.replaceFirst(upperChar.lowercase(), upperChar)
        }
    }

    fun showBluetoothStateNotification(id: Int, state: BluetoothState) {
        val bluetoothStateNotification: Notification = Notification.Builder(
            ctx,
            MainApplication.NOTIFICATION_STATUS_ALERTS_CHANNEL_ID
        ).apply {
            setCategory(Notification.CATEGORY_STATUS)
            setContentTitle(
                ctx.resources.getString(
                    R.string.notification_state_update_title,
                    state.toString()
                )
            )
            setContentText(ctx.resources.getString(R.string.notification_state_update_desc))
        }.build() // why do I have to do this

        notificationManager.notify(id, bluetoothStateNotification)
    }
}