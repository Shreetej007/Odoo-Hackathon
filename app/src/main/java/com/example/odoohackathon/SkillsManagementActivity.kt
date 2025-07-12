package com.example.odoohackathon

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SkillsManagementActivity : AppCompatActivity() {

    private lateinit var layoutSkillsOffered: LinearLayout
    private lateinit var layoutSkillsWanted: LinearLayout
    private lateinit var btnAddSkillOffered: Button
    private lateinit var btnAddSkillWanted: Button
    private lateinit var btnSaveSkills: Button
    private lateinit var btnSaveSkills2: Button  // ✅ New navigation button

    private val skillsOffered = mutableListOf<String>()
    private val skillsWanted = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skills_management)

        layoutSkillsOffered = findViewById(R.id.layoutSkillsOffered)
        layoutSkillsWanted = findViewById(R.id.layoutSkillsWanted)
        btnAddSkillOffered = findViewById(R.id.btnAddSkillOffered)
        btnAddSkillWanted = findViewById(R.id.btnAddSkillWanted)
        btnSaveSkills = findViewById(R.id.btnSaveSkills)
        btnSaveSkills2 = findViewById(R.id.btnSaveSkills2)  // ✅ New button

        btnAddSkillOffered.setOnClickListener {
            showAddSkillDialog(isOffered = true)
        }

        btnAddSkillWanted.setOnClickListener {
            showAddSkillDialog(isOffered = false)
        }

        btnSaveSkills.setOnClickListener {
            Toast.makeText(
                this,
                "Skills saved!\nOffered: $skillsOffered\nWanted: $skillsWanted",
                Toast.LENGTH_LONG
            ).show()
        }

        // ✅ Handle navigation to UserSearchActivity
        btnSaveSkills2.setOnClickListener {
            if (skillsOffered.isEmpty() || skillsWanted.isEmpty()) {
                Toast.makeText(this, "Add both offered and wanted skills first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, UserSearchActivity::class.java).apply {
                putStringArrayListExtra("skillsOffered", ArrayList(skillsOffered))
                putStringArrayListExtra("skillsWanted", ArrayList(skillsWanted))
            }

            startActivity(intent)
            finish()
        }
    }

    private fun showAddSkillDialog(isOffered: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(if (isOffered) "Add Skill Offered" else "Add Skill Wanted")

        val input = EditText(this)
        input.hint = "Enter skill name"
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setPadding(40, 30, 40, 30)

        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val skill = input.text.toString().trim()
            if (skill.isNotEmpty()) {
                if (isOffered) {
                    addSkillToLayout(skill, layoutSkillsOffered, skillsOffered)
                } else {
                    addSkillToLayout(skill, layoutSkillsWanted, skillsWanted)
                }
            } else {
                Toast.makeText(this, "Skill cannot be empty", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun addSkillToLayout(skill: String, container: LinearLayout, list: MutableList<String>) {
        if (list.contains(skill)) {
            Toast.makeText(this, "Skill already added", Toast.LENGTH_SHORT).show()
            return
        }

        list.add(skill)

        val skillLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 4, 0, 4)
            }
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val skillText = TextView(this).apply {
            text = skill
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }

        val removeBtn = ImageButton(this).apply {
            setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            background = null
            setOnClickListener {
                container.removeView(skillLayout)
                list.remove(skill)
            }
        }

        skillLayout.addView(skillText)
        skillLayout.addView(removeBtn)

        container.addView(skillLayout)
    }
}
