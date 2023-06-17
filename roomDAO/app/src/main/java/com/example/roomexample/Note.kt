package com.example.roomexample

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    @ColumnInfo(name = "dateAdded")
    val dateAdded: Date,
    @ColumnInfo(name = "noteText")
    val noteText: String,
//    @ColumnInfo(name = "lastUpdate", defaultValue = "0")
//    val lastUpdate: Int
)