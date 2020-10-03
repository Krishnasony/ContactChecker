package com.example.contactchecker.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactchecker.model.ContactModel
import com.example.contactchecker.repo.ContactListRepo
import com.example.contactchecker.repo.ContactRepo
import com.example.contactchecker.utils.Resource
import com.example.contactchecker.utils.convertedToValidNumber
import kotlinx.coroutines.launch

class ContactViewModel @ViewModelInject constructor(
    private val contactRepo: ContactRepo
) : ViewModel() {
    private var _contacts = MutableLiveData<Resource<List<ContactModel>>>()
    val contacts: LiveData<Resource<List<ContactModel>>>
        get() = _contacts

    var contact = MutableLiveData<ContactModel?>()

    fun fetchContacts() {
        viewModelScope.launch {
            _contacts.postValue(Resource.loading(null))
            contactRepo.getContactList()?.let {
                _contacts.postValue(Resource.success(it))
            } ?: run {
                _contacts.postValue(Resource.error("Something went Wrong!", null))
            }
        }
    }

    fun getContactByNumber(number:String){
        viewModelScope.launch {
            contactRepo.getContactByNumber(number.convertedToValidNumber()).let {
                contact.postValue(it)
            }
        }
    }

}
