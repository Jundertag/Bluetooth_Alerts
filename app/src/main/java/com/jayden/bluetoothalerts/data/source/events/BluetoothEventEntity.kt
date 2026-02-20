package com.jayden.bluetoothalerts.data.source.events

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bluetooth_events",
    indices = [
        Index("timestampMs")
    ]
)
data class BluetoothEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestampMs: Long,
    val eventType: Int,
)