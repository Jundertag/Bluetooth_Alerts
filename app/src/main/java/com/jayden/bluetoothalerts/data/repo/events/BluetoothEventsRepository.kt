package com.jayden.bluetoothalerts.data.repo.events

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jayden.bluetoothalerts.data.source.events.BluetoothEventDatabase

class BluetoothEventsRepository(
    private val applicationContext: Context
) {
    private val database by lazy { BluetoothEventDatabase.get(applicationContext) }
    private val dataAccessObject by lazy { database.getBluetoothEventDao() }

    fun pagingFlow() = Pager(
        config = PagingConfig(
            pageSize = 50,
            prefetchDistance = 20,
            enablePlaceholders = false
        )
    ) {
        dataAccessObject.pagingSource()
    }.flow
}