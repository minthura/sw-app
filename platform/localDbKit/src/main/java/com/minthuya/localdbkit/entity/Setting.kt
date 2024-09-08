package com.minthuya.localdbkit.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minthuya.localdbkit.constant.TableName.TBL_SETTINGS

@Entity(tableName = TBL_SETTINGS)
data class Setting(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val key: String,
    val value: String
)
