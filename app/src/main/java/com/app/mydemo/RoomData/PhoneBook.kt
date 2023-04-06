package com.app.mydemo.RoomData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhoneBook")
data class PhoneBook(@PrimaryKey(autoGenerate = true)var id: Int ? = null,
                     @ColumnInfo(name = "name")var name :String,
                     @ColumnInfo(name = "number")var number : String,
                     @ColumnInfo(name = "address")var address : String)
