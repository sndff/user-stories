package com.saifer.storyapp.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.saifer.storyapp.MainActivity
import com.saifer.storyapp.api.ApiConfig
import com.saifer.storyapp.api.responses.StoriesResponse
import com.saifer.storyapp.databinding.ActivityStoryBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.CustomButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var btnLogout: CustomButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = SessionManager(this@StoryActivity)

        getAllStories(session)

        btnLogout = binding.btnLogout
        btnLogout.setOnClickListener {
            session.logout()
            val i = Intent(this@StoryActivity, MainActivity::class.java)
            startActivity(i)
            finish()
            Toast.makeText(this@StoryActivity, "Berhasil Logout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAllStories(sessionManager: SessionManager){
        val client = ApiConfig.getApiService().getStories("Bearer ${sessionManager.getToken()}")
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Toast.makeText(this@StoryActivity, responseBody.listStory.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@StoryActivity, "Stories not Found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
    }
}