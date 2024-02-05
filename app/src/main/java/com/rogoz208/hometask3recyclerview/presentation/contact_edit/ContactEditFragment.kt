package com.rogoz208.hometask3recyclerview.presentation.contact_edit

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rogoz208.hometask3recyclerview.R
import com.rogoz208.hometask3recyclerview.databinding.FragmentContactEditBinding
import com.rogoz208.hometask3recyclerview.domain.entities.ContactEntity
import dagger.hilt.android.AndroidEntryPoint

private const val EDITING_CONTACT_ARGUMENT = "EDITING_CONTACT_ARGUMENT"
private const val EDITING_CONTACT_POSITION_ARGUMENT = "EDITING_CONTACT_POSITION_ARGUMENT"

@AndroidEntryPoint
class ContactEditFragment : Fragment(R.layout.fragment_contact_edit) {

    private val binding by viewBinding(FragmentContactEditBinding::bind)
    private val viewModel: ContactEditViewModel by viewModels()

    private var editingContact: ContactEntity? = null
    private var editingContactPosition: Int? = null

    companion object {
        const val REQUEST_KEY = "REQUEST_KEY"
        const val SUCCESS_EXTRA_KEY = "SUCCESS_EXTRA_KEY"
        @JvmStatic
        fun newInstance(editingContact: ContactEntity, position: Int) =
            ContactEditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EDITING_CONTACT_ARGUMENT, editingContact)
                    putInt(EDITING_CONTACT_POSITION_ARGUMENT, position)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getContactArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillFieldsWithArguments()
        initViewModel()
        initButtons()
    }

    private fun getContactArguments() {
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                editingContact =
                    it.getParcelable(EDITING_CONTACT_ARGUMENT, ContactEntity::class.java)
                editingContactPosition = it.getInt(EDITING_CONTACT_POSITION_ARGUMENT)
            } else {
                editingContact = it.getParcelable(EDITING_CONTACT_ARGUMENT)
                editingContactPosition = it.getInt(EDITING_CONTACT_POSITION_ARGUMENT)
            }
        }
    }

    private fun fillFieldsWithArguments() {
        editingContact?.let {
            binding.firstNameEditText.setText(it.firstName)
            binding.secondNameEditText.setText(it.secondName)
            binding.phoneNumberEditText.setText(it.phoneNumber)
        }
    }

    private fun initViewModel() {
        viewModel.contactSavedLiveData.observe(viewLifecycleOwner) {
            if (it) {
                setFragmentResult(REQUEST_KEY, bundleOf(SUCCESS_EXTRA_KEY to it))
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun initButtons() {
        binding.saveButton.setOnClickListener {
            val firstName: String = binding.firstNameEditText.text.toString()
            val secondName: String = binding.secondNameEditText.text.toString()
            val phoneNumber: String = binding.phoneNumberEditText.text.toString()

            if (editingContact != null && editingContactPosition != null) {
                viewModel.onContactSaved(
                    editingContact!!.copy(
                        firstName = firstName,
                        secondName = secondName,
                        phoneNumber = phoneNumber
                    ), editingContactPosition
                )
            } else {
                viewModel.onContactSaved(ContactEntity(0, firstName, secondName, phoneNumber), null)
            }
        }
    }
}