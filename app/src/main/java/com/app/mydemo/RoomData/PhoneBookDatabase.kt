package com.app.mydemo.RoomData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhoneBook::class], version = 1)
abstract class PhoneBookDatabase : RoomDatabase() {

    abstract fun phoneBookDao() : PhoneBookDao

    companion object
    {
        private var instance: PhoneBookDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): PhoneBookDatabase
        {
            if(instance == null)
                synchronized(this) {
                    instance = Room.databaseBuilder(ctx.applicationContext, PhoneBookDatabase::class.java, "PhoneBook").build()
                }
            return instance!!

        }
    }
}