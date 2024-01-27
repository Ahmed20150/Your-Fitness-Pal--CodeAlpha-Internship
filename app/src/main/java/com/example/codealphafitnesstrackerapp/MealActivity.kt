package com.example.codealphafitnesstrackerapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

var Meals=mutableListOf<String>()
var mealNum = 1;
class MealActivity: AppCompatActivity() {
     private lateinit var mealInput : EditText
     private lateinit var mealText: TextView
     private lateinit var backBtn: Button
     private lateinit var addBtn: Button
     var currMeal= ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        mealInput = findViewById(R.id.mealInput)
        mealText = findViewById(R.id.mealText)
        backBtn = findViewById(R.id.backBtn)
        addBtn = findViewById(R.id.addBtn)


        mealText.text = retrieveSavedText()

        backBtn.setOnClickListener {
            back()
        }

        addBtn.setOnClickListener {
            addMeal()
        }

    }

    private fun addMeal() {
        currMeal = "Meal "+"$mealNum: ${mealInput.text.toString()}"
        Meals.add(currMeal)
        mealNum++

        saveMealsToSharedPreferences()

        mealText.text = retrieveSavedText()
//        Meals= retrieveMeals()


        mealInput.text.clear()

    }

    private fun retrieveSavedText(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val mealsJson = sharedPreferences.getString("Meals", "")

        val savedMeals: MutableList<String> =
            Gson().fromJson(mealsJson, object : TypeToken<MutableList<String>>() {}.type)
                ?: mutableListOf()

        return savedMeals.joinToString("\n\n") { it }
    }

    private fun saveMealsToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val mealsJson = Gson().toJson(Meals)
        editor.putString("Meals", mealsJson)

        editor.apply()
    }

    private fun retrieveMeals(): MutableList<String> {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val questionsJson = sharedPreferences.getString("Meals", "")


        return Gson().fromJson(questionsJson, object : TypeToken<MutableList<String>>() {}.type)
            ?: mutableListOf()
    }

    private fun back() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}