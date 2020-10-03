package com.example.contactchecker.repo

import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.room.dao.ContactCheckerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactRepo @Inject constructor(
    private val contactCheckerDao: ContactCheckerDao
) : ContactRepoI {

    override suspend fun getContactList(): List<ContactModel>? = withContext(Dispatchers.IO) {
        contactCheckerDao.getContactListWithNickName()
    }

    override suspend fun getContactByNumber(number:String): ContactModel? = withContext(Dispatchers.IO) {
        contactCheckerDao.getContactByNo(number)
    }
}
