package com.example.quizz.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizz.Adapter.OptionAdapter
import com.example.quizz.Models.Question
import com.example.quizz.Models.Quiz
import com.example.quizz.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson



class QuestionActivity : AppCompatActivity() {

    var questions: MutableMap<String, Question>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setupFireStore()
        setUpEventListner()
    }

    private fun setUpEventListner() {
        findViewById<Button>(R.id.btnPrevious).setOnClickListener {
            index--
            bindViews()
        }
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            index++
            bindViews()
        }
        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            Log.d("FINALQUIZ", questions.toString())
            val intent = Intent(this, ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }
    }

    var quizzes: MutableList<Quiz>? = null

        private fun setupFireStore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get().addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("DATA4", "${document.id} => ${document.data}")
                        quizzes = mutableListOf(document.toObject(Quiz::class.java))

                        val questionMap = document.data?.get("questions") as? Map<String, Map<String, String>>
                        if (questionMap != null) {
                            questions = questionMap.mapValues { (_, value) ->
                                Question(
                                    value["description"]?.toString() ?: "",
                                    value["option1"]?.toString() ?: "",
                                    value["option2"]?.toString() ?: "",
                                    value["option3"]?.toString() ?: "",
                                    value["option4"]?.toString() ?: "",
                                    value["answer"]?.toString() ?: ""
                                )

                            }.toMutableMap()
                            quizzes!![0].question = questions as MutableMap<String, Question>
                        } else {
                            questions = mutableMapOf()
                        }
                        Log.d("DATA3", questions.toString() + "-->" + quizzes!![0])
                        bindViews()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }
    }

    private fun bindViews() {
        findViewById<Button>(R.id.btnPrevious).visibility = View.GONE
        findViewById<Button>(R.id.btnNext).visibility = View.GONE
        findViewById<Button>(R.id.btnSubmit).visibility = View.GONE

        if(index == 1) {
            findViewById<Button>(R.id.btnNext).visibility = View.VISIBLE
        }
        else if (index == questions!!.size){
            findViewById<Button>(R.id.btnPrevious).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnSubmit).visibility = View.VISIBLE
        }
        else{
            findViewById<Button>(R.id.btnPrevious).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnNext).visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]
        question?.let {
            findViewById<TextView>(R.id.description).text = question.description
            val optionAdapter = OptionAdapter(this, question)
            findViewById<RecyclerView>(R.id.optionList).layoutManager = LinearLayoutManager(this)
            findViewById<RecyclerView>(R.id.optionList).adapter = optionAdapter
//            optionAdapter?.notifyDataSetChanged()
            findViewById<RecyclerView>(R.id.optionList).setHasFixedSize(true)
        }
    }
}


