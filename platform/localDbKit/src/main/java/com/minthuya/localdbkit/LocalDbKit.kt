package com.minthuya.localdbkit

import com.minthuya.localdbkit.database.AppDatabase

interface LocalDbKit {
    fun getDb(): AppDatabase
}