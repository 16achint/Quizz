package com.example.quizz.Activities.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.quizz.R
import com.google.firebase.auth.FirebaseAuth

class LoginIntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)

        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null)
        {
            Toast.makeText(this,"you are already login",Toast.LENGTH_SHORT).show()
            redirect("Main")
        }

        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            redirect("Login")
        }
    }
    private fun redirect(name : String)
    {
        val intent = when(name)
        {
            "Login" -> Intent(this, LoginActivity::class.java)
            "Main" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("nop path exist")
        }
        startActivity(intent)
        finish()
    }
}