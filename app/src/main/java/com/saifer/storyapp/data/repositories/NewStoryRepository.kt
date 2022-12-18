package com.saifer.storyapp.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.remote.model.PostModel
import com.saifer.storyapp.data.remote.responses.NewStoryResponse
import com.saifer.storyapp.data.remote.retrofit.ApiService
import com.saifer.storyapp.session.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class NewStoryRepository(private val apiService: ApiService) {
    fun postImage(model: PostModel, token: String): LiveData<Boolean> {
        val result = MutableLiveData(false)
        if (model.file != null) {
            val file = reduceFileImage(model.file)
            val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val client = apiService.uploadImage(imageMultipart, model.desc!!, model.lat, model.lon, "Bearer $token")
            client.enqueue(object : Callback<NewStoryResponse> {
                override fun onResponse(
                    call: Call<NewStoryResponse>,
                    response: Response<NewStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error!!) {
                            result.value = true
                        }
                    } else {
                        result.value = false
                    }
                }
                override fun onFailure(call: Call<NewStoryResponse>, t: Throwable) {
                    Log.e("Error On post Story", "Failed to post")
                }
            })
        } else {
            Log.e("Error On post Story", "No picture to post")
        }
        return result
    }

    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
}