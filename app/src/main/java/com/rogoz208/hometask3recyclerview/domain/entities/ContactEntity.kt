package com.rogoz208.hometask3recyclerview.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactEntity(
    val id: Int,
    val firstName: String,
    val secondName: String,
    val phoneNumber: String,
    val isChecked: Boolean = false
) : Parcelable
