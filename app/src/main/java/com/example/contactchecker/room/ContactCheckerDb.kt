package com.example.contactchecker.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.room.dao.ContactCheckerDao

@Database(entities = [ContactModel::class], version = 1)
abstract class ContactCheckerDb : RoomDatabase() {

    abstract val contactCheckerDao: ContactCheckerDao
}
