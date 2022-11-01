package com.saifer.storyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saifer.storyapp.login.LoginActivity
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.story.StoryActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val session = SessionManager(this@MainActivity)

        if (session.checkLogin() == true){
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