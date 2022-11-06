package com.saifer.storyapp.api.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryModel(
    val photoUrl: String,
    val name: String,
    val desc: String,
    val id: String,
    val createdAt: String?,
    val lat: String?,
    val lon: String?
): Parcelable