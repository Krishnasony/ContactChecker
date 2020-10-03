package com.example.contactchecker.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.repo.ContactListRepo
import com.example.contactchecker.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContactListViewModel @ViewModelInject constructor(
    private val contactListRepo: ContactListRepo
) : ViewModel() {

    var _contacts = MutableLiveData<Resource<List<ContactModel>>>()
    private val arrayList: ArrayList<ContactModel> = ArrayList()

    fun getContactDetails() {
        viewModelScope.launch {
            _contacts.postValue(Resource.loading(null))
            val map = contactListRepo.getContactList()
                .filter { it.value.number.isNotEmpty() && it.value.number.length >= 10 }
            if (map.entries.isNotEmpty())
                sortArrayList(map)
            else _contacts.postValue(Resource.error("Something went wrong!", null))
        }
    }

    fun addContactToDataBase(contactModel: ContactModel) {
        viewModelScope.launch {
            contactListRepo.addNickName(contactModel)
        }
    }

    private suspend fun sortArrayList(hashMap: Map<String?, ContactModel>) =
        withContext(Dispatchers.IO) {
            val dbList = contactListRepo.getContacts()
            arrayList.clear()
            for ((_, value) in hashMap.entries) arrayList.add(
                value
            )
            if (dbList.isNullOrEmpty()) {
                arrayList.sortWith { p0, p1 ->
                    if (p0.name.isNullOrEmpty() || p1.name.isNullOrEmpty()) p0.number.compareTo(
                        p1.number,
                        true
                    )
                    else p0.name.compareTo(p1.name, true)
                }
                _contacts.postValue(Resource.success(arrayList))
            } else {
                val filterList = dbList.flatMap { contact ->
                    arrayList.filter {
                        it.number.equals(
                            contact.number,
                            true
                        )
                    }
                }
                arrayList.removeAll(filterList)
                arrayList.sortWith { p0, p1 ->
                    if (p0.name.isNullOrEmpty() || p1.name.isNullOrEmpty()) p0.number.compareTo(
                        p1.number,
                        true
                    )
                    else p0.name.compareTo(p1.name, true)
                }
                _contacts.postValue(Resource.success(arrayList))
            }
        }
}