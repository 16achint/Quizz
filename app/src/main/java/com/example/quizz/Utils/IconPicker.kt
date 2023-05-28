package com.example.quizz.Utils

import com.example.quizz.R

object IconPicker {
    val Icon = arrayListOf(R.drawable.icon_1,R.drawable.icon_2,R.drawable.icon_3,R.drawable.icon_4,R.drawable.icon_5,R.drawable.icon_6,R.drawable.icon_7,R.drawable.icon_8,)
    var currentCIcon = 0;

    fun getIcon():Int {
        currentCIcon = (currentCIcon+1)% Icon.size

        return Icon[currentCIcon]
    }

}