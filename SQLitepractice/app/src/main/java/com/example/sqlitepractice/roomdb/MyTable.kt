package com.example.sqlitepractice.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class MyTable (
    val rank:Int,
    @PrimaryKey val title: String,
    val artist:String,
    val album:String,
    val num_like:Int
)