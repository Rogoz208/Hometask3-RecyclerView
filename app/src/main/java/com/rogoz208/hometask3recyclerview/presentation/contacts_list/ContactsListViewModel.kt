package com.rogoz208.hometask3recyclerview.presentation.contacts_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity
import com.rogoz208.hometask3recyclerview.domain.repos.ContactsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsListViewModel @Inject constructor(
    private val contactsRepo: ContactsRepo
) : ViewModel() {

    private val _contactsListLiveData = MutableLiveData<List<ContactEntity>>()
    val contactsListLiveData: LiveData<List<ContactEntity>> = _contactsListLiveData

    init {
        updateContactsList()
    }

    fun onDeleteMultipleContacts(contacts: List<ContactEntity>) {
        contactsRepo.deleteMultipleContacts(contacts)
        updateContactsList()
    }

    fun onContactUpdated() {
        updateContactsList()
    }

    fun onUpdateContact(contact: ContactEntity, position: Int) {
        contactsRepo.updateContact(contact.id, contact, position)
        updateContactsList()
    }

    private fun updateContactsList() {
        contactsRepo.getContacts { contacts ->
            _contactsListLiveData.postValue(contacts)
        }
    }
}