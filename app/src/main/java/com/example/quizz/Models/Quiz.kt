package com.example.quizz.Models

data class Quiz (
    var id : String = "",
    var title : String = "",
    var question : MutableMap<String,Question> = mutableMapOf()
    )