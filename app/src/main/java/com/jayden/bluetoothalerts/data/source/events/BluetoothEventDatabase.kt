package com.jayden.bluetoothalerts.data.source.events

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [BluetoothEventEntity::class])
abstract class BluetoothEventDatabase : RoomDatabase() {
    abstract fun getBluetoothEventDao(): BluetoothEventDao

    companion object {
        @Volatile private var INSTANCE: BluetoothEventDatabase? = null

        fun get(context: Context): BluetoothEventDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BluetoothEventDatabase::class.java,
                    "bluetooth_events.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}