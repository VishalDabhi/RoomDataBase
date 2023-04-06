package com.app.mydemo.RoomData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhoneBookDao {

    @Insert
    suspend fun insert(phoneBook: PhoneBook)

    @Update
    suspend fun update(phoneBook: PhoneBook)

    @Delete
    suspend fun delete(phoneBook: PhoneBook)

    @Query("delete from PhoneBook")
    suspend fun deleteAllNumber()

    @Query("SELECT * FROM PhoneBook ORDER BY name ASC")
    fun getNotes(): List<PhoneBook>
}