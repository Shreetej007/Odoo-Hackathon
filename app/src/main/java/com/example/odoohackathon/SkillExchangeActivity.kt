package com.example.odoohackathon

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SkillExchangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_exchange)

        val spinnerOfferedSkill = findViewById<Spinner>(R.id.spinnerOfferedSkill)
        val spinnerWantedSkill = findViewById<Spinner>(R.id.spinnerWantedSkill)
        val btnSubmitRequest = findViewById<Button>(R.id.btnSubmitRequest)

        // Dropdown values
        val yourSkills = listOf("Web Development", "Android Development")
        val theirSkills = listOf("Java", "Kotlin")

        // Set adapters
        spinnerOfferedSkill.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yourSkills)
        spinnerWantedSkill.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, theirSkills)

        // Navigate to RequestStatus screen on button click
        btnSubmitRequest.setOnClickListener {
            val intent = Intent(this, FInalPageAcivity::class.java)
            startActivity(intent)
        }
    }
}
