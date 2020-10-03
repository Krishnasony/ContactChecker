package com.example.contactchecker.repo

import com.example.contactchecker.model.ContactModel

interface ContactRepoI {

    suspend fun getContactList(): List<ContactModel>?

    suspend fun getContactByNumber(number: String): ContactModel?

}