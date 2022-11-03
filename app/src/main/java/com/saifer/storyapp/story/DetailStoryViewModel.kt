package com.saifer.storyapp.story

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saifer.storyapp.api.ApiConfig
import com.saifer.storyapp.api.data.StoryModel
import com.saifer.storyapp.api.responses.DetailStoryResponse
import com.saifer.storyapp.databinding.ActivityDetailStoryBinding
import com.saifer.storyapp.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel : ViewModel(){

    fun getDetailStory(activity: AppCompatActivity, binding: ActivityDetailStoryBinding, id: String, sessionManager: SessionManager){
        val client = ApiConfig.getApiService().getDetailStory(id, "Bearer ${sessionManager.getToken()}")
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){

                        Glide.with(activity)
                            .load(responseBody.story?.photoUrl)
                            .apply( RequestOptions().override(1000,1000))
                            .into(binding.ivDetailPhoto)
                        binding.tvDetailName.text = responseBody.story?.name
                        binding.tvDetailDescription.text = responseBody.story?.description
                    }
                } else {
                    Toast.makeText(activity, "Stories not Found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
    }

}