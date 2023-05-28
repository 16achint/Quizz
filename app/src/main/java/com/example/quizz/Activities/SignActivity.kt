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

class SignActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        firebaseAuth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.btnSign).setOnClickListener {
            signup()
        }

        findViewById<TextView>(R.id.login).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signup() {

        val email = findViewById<EditText>(R.id.etEmail).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.etComfirmPassowrd).text.toString()

        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank())
        {
            Toast.makeText(this,"Email and password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if(password!=confirmPassword)
        {
            Toast.makeText(this,"password and confirm Password do not match",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful)
            {
                Toast.makeText(this,"Login Successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this,"error create user",Toast.LENGTH_SHORT).show()
            }
        }
    }


}