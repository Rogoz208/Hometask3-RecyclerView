package com.rogoz208.hometask3recyclerview.data.repos

import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity
import com.rogoz208.hometask3recyclerview.domain.repos.ContactsRepo
import kotlin.random.Random

class MemoryCacheContactsMockRepoImpl : ContactsRepo {

    private val cache: MutableList<ContactEntity> = mutableListOf()

    init {
        fillRepoWithMockValues()
    }

    override fun getContacts(callback: (List<ContactEntity>) -> Unit) =
        callback(ArrayList<ContactEntity>(cache))

    override fun createContact(contact: ContactEntity) {
        val newId = Random.nextInt()
        cache.add(contact.copy(id = newId))
    }

    override fun deleteContact(id: Int) {
        for (i in cache.indices) {
            if (cache[i].id == id) {
                cache.removeAt(i)
                return
            }
        }
    }

    override fun deleteMultipleContacts(contacts: List<ContactEntity>) {
        contacts.forEach {
            deleteContact(it.id)
        }
    }

    override fun updateContact(id: Int, contact: ContactEntity, position: Int) {
        deleteContact(id)
        cache.add(position, contact.copy(id = id))
    }

    private fun fillRepoWithMockValues() {
        val firstNames = listOf("Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack", "Kate", "Liam", "Mia", "Noah", "Olivia", "Peter", "Quinn", "Rose", "Samuel", "Tara")
        val lastNames = listOf("Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin")

        repeat(100) {
            val id = it + 1
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val phoneNumber = "+7${(9000000000..9999999999).random()}"

            val contact = ContactEntity(id, firstName, lastName, phoneNumber)
            createContact(contact)
        }
    }
}