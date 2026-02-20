package com.jayden.bluetoothalerts.app.service

import android.app.Notification
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication
import com.jayden.bluetoothalerts.app.notifications.AppNotificationRegistry
import com.jayden.bluetoothalerts.app.receivers.BluetoothDeviceEventReceiver
import com.jayden.bluetoothalerts.app.receivers.BluetoothEventReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BluetoothAlertService : Service() {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    inner class LocalBinder : Binder() {
        fun getService(): BluetoothAlertService = this@BluetoothAlertService
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val eventReceiver = BluetoothEventReceiver()
    private val deviceEventReceiver = BluetoothDeviceEventReceiver()
    private var eventReceiverRegistered = false
    private var deviceEventReceiverRegistered = false

    private var settingsJob: Job? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand(intent = $intent, flags = $flags, startId = $startId)")

        val settingsRepo = (application as MainApplication).settingsRepository

        val notification: Notification = Notification.Builder(
            this@BluetoothAlertService,
            AppNotificationRegistry.NOTIFICATION_BLUETOOTH_ALERT_SERVICE_CHANNEL_ID
        ).apply {
            setContentTitle(resources.getString(R.string.notification_foreground_service_title))
            setContentText(resources.getString(R.string.notification_foreground_service_desc))
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setCategory(Notification.CATEGORY_SERVICE)
            setLocalOnly(true)
            setShowWhen(false)
        }.build()

        Log.i(TAG, "Service moving to foreground with id of: $FOREGROUND_ID")
        startForeground(FOREGROUND_ID, notification)
        Log.i(TAG, "Registering event receivers")

        if (settingsJob == null) {
            settingsJob = serviceScope.launch {
                settingsRepo.settingsFlow(this).map { it.foregroundServiceEnabled }.distinctUntilChanged()
                    .collect { enabled ->
                        if (enabled && !eventReceiverRegistered && !deviceEventReceiverRegistered) {
                            Log.i(TAG, "Registering All Receiver")
                            registerReceivers()
                        } else if (enabled && !eventReceiverRegistered) {
                            Log.i(TAG, "Registering Adapter Receiver")
                            registerAdapterReceiver()
                        } else if (enabled && !deviceEventReceiverRegistered) {
                            Log.i(TAG, "Registering Device Receiver")
                            registerDeviceReceiver()
                        } else {
                            Log.i(TAG, "foregroundServiceEnabled == $enabled")
                        }
                    }
            }
        }

        return START_STICKY
    }

    private fun registerReceivers() {
        registerDeviceReceiver()
        registerAdapterReceiver()
    }

    private fun registerAdapterReceiver() {
        registerReceiver(
            eventReceiver, IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
                addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED)
                addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
            }, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                RECEIVER_EXPORTED
            else 0
        )
        eventReceiverRegistered = true
    }

    private fun registerDeviceReceiver() {
        registerReceiver(
            deviceEventReceiver, IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
                addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    addAction(BluetoothDevice.ACTION_ALIAS_CHANGED)
                }
                addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
                addAction(BluetoothDevice.ACTION_CLASS_CHANGED)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                    addAction(BluetoothDevice.ACTION_ENCRYPTION_CHANGE)
                }
                addAction(BluetoothDevice.ACTION_FOUND)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                    addAction(BluetoothDevice.ACTION_KEY_MISSING)
                }
                addAction(BluetoothDevice.ACTION_NAME_CHANGED)
                addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
                addAction(BluetoothDevice.ACTION_UUID)
            }, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                RECEIVER_EXPORTED
            else 0
        )
        deviceEventReceiverRegistered = true
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        Log.i(TAG, "Unregistering event receiver")
        if (eventReceiverRegistered) {
            unregisterReceiver(eventReceiver)
            eventReceiverRegistered = false
        }
        if (deviceEventReceiverRegistered) {
            unregisterReceiver(deviceEventReceiver)
            deviceEventReceiverRegistered = false
        }
        settingsJob?.cancel()
        settingsJob = null
        serviceJob.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "BluetoothAlertService"
        const val FOREGROUND_ID = 131072
    }
}