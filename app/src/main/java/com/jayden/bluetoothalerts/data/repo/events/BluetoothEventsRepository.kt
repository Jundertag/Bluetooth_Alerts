package com.jayden.bluetoothalerts.data.repo.events

import com.jayden.bluetoothalerts.data.source.events.BluetoothEventDataStore
import com.jayden.bluetoothalerts.proto.BluetoothEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BluetoothEventsRepository(
    private val dataStore: BluetoothEventDataStore
) {
    fun bluetoothEventsFlow(scope: CoroutineScope) = dataStore.bluetoothEventFlow.stateIn(
        scope,
        SharingStarted.WhileSubscribed(5_000),
        BluetoothEvent.getDefaultInstance()
    )
}