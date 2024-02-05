package com.rogoz208.hometask3recyclerview.presentation.contact_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity
import com.rogoz208.hometask3recyclerview.domain.repos.ContactsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactEditViewModel @Inject constructor(
    private val contactsRepo: ContactsRepo
) : ViewModel() {

    private val _contactSavedLiveData = MutableLiveData<Boolean>(false)
    val contactSavedLiveData: LiveData<Boolean> = _contactSavedLiveData

    fun onContactSaved(contact: ContactEntity, position: Int?) {
        if (position == null) {
            contactsRepo.createContact(contact)
            _contactSavedLiveData.postValue(true)
        } else {
            contactsRepo.updateContact(contact.id, contact, position)
            _contactSavedLiveData.postValue(true)
        }
    }
}