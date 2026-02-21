package com.jayden.bluetoothalerts.data.source.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface BluetoothEventDao {
    @Insert
    suspend fun insertBluetoothEvent(event: BluetoothEventEntity)

    @Delete
    suspend fun deleteBluetoothEvent(event: BluetoothEventEntity)

    @Query("""
        SELECT * FROM bluetooth_events
        ORDER BY timestampMs DESC, id DESC
    """)
    fun pagingSource(): PagingSource<Int, BluetoothEventEntity>
}