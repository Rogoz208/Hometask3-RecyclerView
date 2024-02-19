package com.rogoz208.hometask3recyclerview.presentation.contacts_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rogoz208.hometask3recyclerview.R
import com.rogoz208.hometask3recyclerview.databinding.FragmentContactsListBinding
import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity
import com.rogoz208.hometask3recyclerview.presentation.contact_edit.ContactEditFragment
import com.rogoz208.hometask3recyclerview.presentation.contacts_list.recycler.ContactsAdapter
import com.rogoz208.hometask3recyclerview.presentation.contacts_list.recycler.ContactsDiffCallback
import com.rogoz208.hometask3recyclerview.presentation.contacts_list.recycler.OnContactItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    private val binding by viewBinding(FragmentContactsListBinding::bind)
    private val viewModel: ContactsListViewModel by viewModels()
    private val menuHost: MenuHost get() = requireActivity()

    private var adapter = ContactsAdapter()
    private var isSelectionMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(ContactEditFragment.REQUEST_KEY) { key, bundle ->
            val result = bundle.getBoolean(ContactEditFragment.SUCCESS_EXTRA_KEY)
            if (result) {
                viewModel.onContactUpdated()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        initToolbarMenu()
        initButtons()
    }

    private fun initViewModel() {
        viewModel.contactsListLiveData.observe(viewLifecycleOwner) { contacts ->
            fillRecyclerView(contacts)
        }
    }

    private fun initRecyclerView() {
        adapter.setOnItemClickListener(object : OnContactItemClickListener {
            override fun onItemClick(item: ContactEntity, position: Int) {
                if (isSelectionMode) {
                    viewModel.onUpdateContact(item.copy(isChecked = !item.isChecked), position)
                } else {
                    openEditContactScreen(item, position)
                }
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun initToolbarMenu() {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) {
                    menuInflater.inflate(R.menu.activity_main_toolbar_menu, menu)
                }

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.delete_menu_item -> {
                        if (isSelectionMode) {
                            deleteNotes()
                        } else {
                            toggleSelectionMode()
                        }
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun initButtons() {
        binding.deleteButton.setOnClickListener {
            if (isSelectionMode) {
                deleteNotes()
            }
        }

        binding.cancelButton.setOnClickListener {
            toggleSelectionMode()
        }

        binding.addButton.setOnClickListener {
            openEditContactScreen(null, null)
        }
    }

    private fun deleteNotes() {
        val selectedItems = adapter.getSelectedItems()
        viewModel.onDeleteMultipleContacts(selectedItems)
    }

    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        binding.addButton.visibility = if (isSelectionMode) View.GONE else View.VISIBLE
        binding.deleteButton.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
        binding.cancelButton.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
        adapter.data.forEachIndexed { index, item ->
            if (item.isChecked) {
                viewModel.onUpdateContact(item.copy(isChecked = false), index)
            }
        }
        adapter.setSelectionMode(isSelectionMode)
    }

    private fun fillRecyclerView(contacts: List<ContactEntity>) {
        val contactsDiffCallback = ContactsDiffCallback(adapter.data, contacts)
        val result = DiffUtil.calculateDiff(contactsDiffCallback, true)
        adapter.data = contacts
        result.dispatchUpdatesTo(adapter)
    }

    private fun openEditContactScreen(contact: ContactEntity?, position: Int?) {
        if (contact != null && position != null) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ContactEditFragment.newInstance(contact, position)
                )
                .addToBackStack(null)
                .commit()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ContactEditFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}