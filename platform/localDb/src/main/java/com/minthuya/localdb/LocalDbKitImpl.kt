package com.minthuya.localdb

import android.content.Context
import androidx.room.Room
import com.minthuya.localdbkit.LocalDbKit
import com.minthuya.localdbkit.database.AppDatabase

class LocalDbKitImpl(
    private val context: Context
) : LocalDbKit {
    override fun getDb(): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "stationsdb"
        ).build()
}