package com.saifer.storyapp.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.saifer.storyapp.MainActivity
import com.saifer.storyapp.databinding.ActivityStoryBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.CustomButton

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var btnLogout: CustomButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = SessionManager(this@StoryActivity)

        btnLogout = binding.btnLogout
        btnLogout.setOnClickListener {
            session.logout()
            val i = Intent(this@StoryActivity, MainActivity::class.java)
            startActivity(i)
            finish()
            Toast.makeText(this@StoryActivity, "Berhasil Logout", Toast.LENGTH_SHORT).show()
        }
    }
}