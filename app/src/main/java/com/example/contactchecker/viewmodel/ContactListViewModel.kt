package com.example.contactchecker.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.repo.ContactListRepo
import com.example.contactchecker.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContactListViewModel @ViewModelInject constructor(
    application: Application,
    private val contactListRepo: ContactListRepo
) : AndroidViewModel(application) {

    var _contacts = MutableLiveData<Resource<ArrayList<ContactModel>>>()
    private val arrayList: ArrayList<ContactModel> = ArrayList()

    fun getContactDetails() {
        viewModelScope.launch {
            _contacts.postValue(Resource.loading(null))
            val map = contactListRepo.getContactList()
            if (map.entries.isNotEmpty())
                sortArrayList(map)
            else _contacts.postValue(Resource.error("Something went wrong!", null))
        }
    }

    fun addContactToDataBase(contactModel: ContactModel){
        viewModelScope.launch {
            contactListRepo
        }
    }

    private suspend fun sortArrayList(hashMap: HashMap<String?, ContactModel>) =
        withContext(Dispatchers.IO) {
            arrayList.clear()
            for ((_, value) in hashMap.entries) arrayList.add(
                value
            )
            arrayList.sortWith { p0, p1 -> p0.number.compareTo(p1.number, true) }
            _contacts.postValue(Resource.success(arrayList))

        }
}