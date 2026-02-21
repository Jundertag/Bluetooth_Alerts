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
    val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BluetoothEventEntity

        if (id != other.id) return false
        if (timestampMs != other.timestampMs) return false
        if (eventType != other.eventType) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + timestampMs.hashCode()
        result = 31 * result + eventType
        result = 31 * result + data.contentHashCode()
        return result
    }
}