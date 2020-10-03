package com.example.contactchecker.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contactchecker.model.ContactModel

@Dao
interface ContactCheckerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContactToDb(contactModel: ContactModel)

    @Query("SELECT * FROM contact")
    fun getContactListWithNickName():List<ContactModel>

}