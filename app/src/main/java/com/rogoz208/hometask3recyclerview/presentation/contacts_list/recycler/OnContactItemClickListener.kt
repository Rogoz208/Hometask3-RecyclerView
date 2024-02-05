package com.rogoz208.hometask3recyclerview.presentation.contacts_list.recycler

import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity

interface OnContactItemClickListener {

    fun onItemClick(item: ContactEntity, position: Int)
}