package com.saifer.storyapp.data.remote.model

import okhttp3.RequestBody
import java.io.File

data class PostModel(
    val file: File?,
    val desc: RequestBody?,
    val lat: RequestBody?,
    val lon: RequestBody?
)
