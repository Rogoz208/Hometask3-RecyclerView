package com.rogoz208.hometask3recyclerview.domain.repos

import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity

interface ContactsRepo {

    fun getContacts(callback: (List<ContactEntity>) -> Unit)

    fun createContact(contact: ContactEntity)

    fun deleteContact(id: Int)

    fun deleteMultipleContacts(contacts: List<ContactEntity>)

    fun updateContact(id: Int, contact: ContactEntity, position: Int)
}