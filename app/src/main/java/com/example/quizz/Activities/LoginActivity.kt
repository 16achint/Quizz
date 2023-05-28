package com.example.quizz.Activities.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizz.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            login()
        }
        findViewById<TextView>(R.id.signIn).setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        if(email.isBlank() || password.isBlank())
        {
            Toast.makeText(this,"email and password can not be blank",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this)
        {
            if(it.isSuccessful)
            {
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
            }
        }


    }
}