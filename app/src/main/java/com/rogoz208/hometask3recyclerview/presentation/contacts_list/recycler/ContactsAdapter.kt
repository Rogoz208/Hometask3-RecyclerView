package com.rogoz208.hometask3recyclerview.presentation.contacts_list.recycler

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity

class ContactsAdapter : RecyclerView.Adapter<ContactViewHolder>() {

    var data: List<ContactEntity> = ArrayList()
        get() = ArrayList(field)
    private var isSelectionMode = false
    private var clickListener: OnContactItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(parent, clickListener!!)

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position), isSelectionMode)
    }


    override fun getItemCount(): Int = data.size

    fun getItem(position: Int): ContactEntity = data[position]

    fun getSelectedItems(): List<ContactEntity> {
        val selectedItems = mutableListOf<ContactEntity>()
        data.forEach {
            if (it.isChecked) {
                selectedItems.add(it)
            }
        }
        return selectedItems
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectionMode(selectionMode: Boolean) {
        isSelectionMode = selectionMode
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnContactItemClickListener) {
        clickListener = listener
    }
}