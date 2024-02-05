package com.rogoz208.hometask3recyclerview.presentation.contacts_list.recycler

import androidx.recyclerview.widget.DiffUtil
import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity

class ContactsDiffCallback(
    private val oldList: List<ContactEntity>,
    private val newList: List<ContactEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].isChecked == newList[newItemPosition].isChecked
                && oldList[oldItemPosition].firstName == newList[newItemPosition].firstName
                && oldList[oldItemPosition].secondName == newList[newItemPosition].secondName
                && oldList[oldItemPosition].phoneNumber == newList[newItemPosition].phoneNumber
}