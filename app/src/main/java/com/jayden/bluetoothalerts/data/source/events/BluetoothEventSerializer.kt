package com.jayden.bluetoothalerts.data.source.events

import com.google.protobuf.InvalidProtocolBufferException
import com.jayden.bluetoothalerts.proto.BluetoothEvent

object BluetoothEventSerializer {
    fun BluetoothEvent.toEntity() = BluetoothEventEntity(
            timestampMs = this.timestamp,
            eventType = when (this.eventCase) {
                BluetoothEvent.EventCase.NAME_CHANGED_EVENT -> EventType.NAME_CHANGED
                BluetoothEvent.EventCase.CONNECTION_STATE_CHANGED_EVENT -> EventType.CONNECTION_STATE_CHANGED
                BluetoothEvent.EventCase.DISCOVERY_STATE_CHANGED_EVENT -> EventType.DISCOVERY_STATE_CHANGED
                BluetoothEvent.EventCase.SCAN_MODE_CHANGED_EVENT -> EventType.SCAN_MODE_CHANGED
                BluetoothEvent.EventCase.STATE_CHANGED_EVENT -> EventType.STATE_CHANGED
                BluetoothEvent.EventCase.DEVICE_CONNECTED_EVENT -> EventType.DEVICE_CONNECTED
                BluetoothEvent.EventCase.DEVICE_DISCONNECTED_EVENT -> EventType.DEVICE_DISCONNECTED
                BluetoothEvent.EventCase.DEVICE_DISCONNECT_REQUESTED_EVENT -> EventType.DEVICE_DISCONNECT_REQUESTED
                BluetoothEvent.EventCase.ALIAS_CHANGED_EVENT -> EventType.ALIAS_CHANGED
                BluetoothEvent.EventCase.BOND_STATE_CHANGED_EVENT -> EventType.BOND_STATE_CHANGED
                BluetoothEvent.EventCase.CLASS_CHANGED_EVENT -> EventType.CLASS_CHANGED
                BluetoothEvent.EventCase.ENCRYPTION_CHANGED_EVENT -> EventType.ENCRYPTION_CHANGED
                BluetoothEvent.EventCase.DEVICE_FOUND_EVENT -> EventType.DEVICE_FOUND
                BluetoothEvent.EventCase.KEY_MISSING_EVENT -> EventType.KEY_MISSING
                BluetoothEvent.EventCase.PAIRING_REQUEST_EVENT -> EventType.PAIRING_REQUEST
                BluetoothEvent.EventCase.UUID_FOUND_EVENT -> EventType.UUID_FOUND
                else -> EventType.UNKNOWN // this will never happen unless I fall asleep
            },
            data = this.toByteArray()
        )

    fun BluetoothEventEntity.toProto(): BluetoothEvent = BluetoothEvent.parseFrom(this.data)
    fun BluetoothEventEntity.toProtoOrNull(): BluetoothEvent? = try {
        BluetoothEvent.parseFrom(this.data)
    } catch (_: InvalidProtocolBufferException) {
        null
    }

    fun BluetoothEvent.toEventType(): Int = when (this.eventCase) {
        BluetoothEvent.EventCase.NAME_CHANGED_EVENT -> EventType.NAME_CHANGED
        BluetoothEvent.EventCase.CONNECTION_STATE_CHANGED_EVENT -> EventType.CONNECTION_STATE_CHANGED
        BluetoothEvent.EventCase.DISCOVERY_STATE_CHANGED_EVENT -> EventType.DISCOVERY_STATE_CHANGED
        BluetoothEvent.EventCase.SCAN_MODE_CHANGED_EVENT -> EventType.SCAN_MODE_CHANGED
        BluetoothEvent.EventCase.STATE_CHANGED_EVENT -> EventType.STATE_CHANGED
        BluetoothEvent.EventCase.DEVICE_CONNECTED_EVENT -> EventType.DEVICE_CONNECTED
        BluetoothEvent.EventCase.DEVICE_DISCONNECTED_EVENT -> EventType.DEVICE_DISCONNECTED
        BluetoothEvent.EventCase.DEVICE_DISCONNECT_REQUESTED_EVENT -> EventType.DEVICE_DISCONNECT_REQUESTED
        BluetoothEvent.EventCase.ALIAS_CHANGED_EVENT -> EventType.ALIAS_CHANGED
        BluetoothEvent.EventCase.BOND_STATE_CHANGED_EVENT -> EventType.BOND_STATE_CHANGED
        BluetoothEvent.EventCase.CLASS_CHANGED_EVENT -> EventType.CLASS_CHANGED
        BluetoothEvent.EventCase.ENCRYPTION_CHANGED_EVENT -> EventType.ENCRYPTION_CHANGED
        BluetoothEvent.EventCase.DEVICE_FOUND_EVENT -> EventType.DEVICE_FOUND
        BluetoothEvent.EventCase.KEY_MISSING_EVENT -> EventType.KEY_MISSING
        BluetoothEvent.EventCase.PAIRING_REQUEST_EVENT -> EventType.PAIRING_REQUEST
        BluetoothEvent.EventCase.UUID_FOUND_EVENT -> EventType.UUID_FOUND
        else -> EventType.UNKNOWN // this will never happen unless I fall asleep
    }

    private const val TAG = "BluetoothEventSerializer"
}