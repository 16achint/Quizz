package com.example.quizz.Activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.example.quizz.Activities.Activities.MainActivity
import com.example.quizz.Models.Quiz
import com.example.quizz.R
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    lateinit var quiz : Quiz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpView()
        findViewById<Button>(R.id.btnHome).setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setUpView() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData,Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for(entry in quiz.question.entries) {
            val question = entry.value
            builder.append("<font color '#18206F'><b>Question: ${question.description}</br></font><br/><br/>")
            builder.append("<font color '#009688'><b>Answer: ${question.answer}</font><br/><br/>")
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            findViewById<TextView>(R.id.txtAnswer).text = Html.fromHtml(builder.toString(),Html.FROM_HTML_MODE_COMPACT);
        }
        else{
            findViewById<TextView>(R.id.txtAnswer).text = Html.fromHtml(builder.toString())
        }
    }

    private fun calculateScore() {
        var score = 0
        for(entry in quiz.question.entries) {
            val question = entry.value
            if(question.answer == question.userAnswer){
                score += 10
            }
        }
        findViewById<TextView>(R.id.txtScore).text = "Your Score : $score"
    }
    override fun onBackPressed(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}