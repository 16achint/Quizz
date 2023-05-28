package com.example.quizz.Activities.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.compose.material3.MaterialTheme
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizz.Activities.ProfileActivity
import com.example.quizz.Activities.QuestionActivity
import com.example.quizz.Adapter.QuizAdapter
import com.example.quizz.Models.Quiz
import com.example.quizz.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()

        progressLayout = findViewById(R.id.progressLayout)
        progressBar = findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

    }
    private fun setUpViews() {
        setUpFireStore()
        setUpDrawLayout()
        setRecyclerView()
        setupDatePicker()
    }

    private fun setupDatePicker() {
        findViewById<FloatingActionButton>(R.id.btnDataPicker).setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER",datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener{
                Log.d("DATEPICKER",datePicker.headerText)
            }
            datePicker.addOnCancelListener{
                Log.d("DATEPICKER","DatePicker was Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        val db = Firebase.firestore
        db.collection("quizzes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    progressLayout.visibility = View.GONE
                    Log.d("DATA", "${document.id} => ${document.data}")
                }
                quizList.clear()
                quizList.addAll(result.toObjects(Quiz::class.java))
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                Log.w("DATA", "Error getting documents.", exception)
            }
    }

    private fun setRecyclerView() {
        adapter = QuizAdapter(this,quizList)
        findViewById<RecyclerView>(R.id.quizRecycleView).layoutManager = GridLayoutManager(this,2)
        findViewById<RecyclerView>(R.id.quizRecycleView).adapter = adapter
    }

    private fun setUpDrawLayout() {
        setSupportActionBar(findViewById(R.id.appBar))
        actionBarDrawerToggle = ActionBarDrawerToggle(this,findViewById(R.id.mainDrawer),
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        findViewById<NavigationView>(R.id.navigationView).setNavigationItemSelectedListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            findViewById<DrawerLayout>(R.id.mainDrawer).closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }
}