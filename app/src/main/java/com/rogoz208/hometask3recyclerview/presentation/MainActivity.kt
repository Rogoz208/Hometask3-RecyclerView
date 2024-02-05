package com.rogoz208.hometask3recyclerview.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rogoz208.hometask3recyclerview.R
import com.rogoz208.hometask3recyclerview.databinding.ActivityMainBinding
import com.rogoz208.hometask3recyclerview.presentation.contacts_list.ContactsListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        openDefaultScreen(savedInstanceState)
    }

    private fun openDefaultScreen(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainer.id, ContactsListFragment())
                .commit()
        }
    }
}