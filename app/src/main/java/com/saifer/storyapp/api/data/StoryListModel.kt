package com.saifer.storyapp.api.data

data class StoryListModel(
    val id: String,
    val name: String,
    val desc: String,
    val photoUrl: String,
    val timestamp: String,
    val lat: String,
    val lon: String
)
