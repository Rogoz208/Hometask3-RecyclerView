package com.rogoz208.hometask3recyclerview.presentation.contacts_list.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rogoz208.hometask3recyclerview.R
import com.rogoz208.hometask3recyclerview.databinding.ItemContactViewHolderBinding
import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity

class ContactViewHolder(parent: ViewGroup, private val clickListener: OnContactItemClickListener) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_view_holder, parent, false)
    ) {

    private val binding by viewBinding(ItemContactViewHolderBinding::bind)

    @SuppressLint("SetTextI18n")
    fun bind(contact: ContactEntity, isSelectionMode: Boolean) {
        binding.nameTextView.text = "${contact.firstName} ${contact.secondName}"
        binding.phoneNumberTextView.text = contact.phoneNumber
        binding.isSelectedCheckBox.apply {
            visibility = if (isSelectionMode) View.VISIBLE else View.GONE
            isChecked = contact.isChecked
        }

        itemView.setOnClickListener {
            clickListener.onItemClick(contact, this.layoutPosition)
        }
    }
}