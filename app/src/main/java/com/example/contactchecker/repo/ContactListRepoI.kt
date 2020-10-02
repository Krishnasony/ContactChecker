package com.example.contactchecker.repo

import com.example.contactchecker.model.ContactModel

interface ContactListRepoI {

    suspend fun getContactList(): HashMap<String?, ContactModel>

    suspend fun addNickName(contactModel: ContactModel)

}