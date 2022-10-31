package com.saifer.storyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.saifer.storyapp.login.LoginActivity
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.story.StoryActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val session = SessionManager(this@MainActivity)

        if (session.checkLogin() == true){
            Toast.makeText(this@MainActivity, session.getToken(), Toast.LENGTH_SHORT).show()
            // langsung masuk ke story Activity
            val i = Intent(this@MainActivity, StoryActivity::class.java)
            startActivity(i)
            finish()
        } else {
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}