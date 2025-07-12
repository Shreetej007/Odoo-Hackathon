package com.example.odoohackathon

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var imageProfilePhoto: ImageView
    private lateinit var btnChangePhoto: Button
    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var cbWeekends: CheckBox
    private lateinit var cbEvenings: CheckBox
    private lateinit var rgVisibility: RadioGroup
    private lateinit var btnSaveProfile: Button

    private lateinit var layoutSkillsOffered: LinearLayout
    private lateinit var layoutSkillsWanted: LinearLayout
    private lateinit var btnAddSkillOffered: Button
    private lateinit var btnAddSkillWanted: Button

    private val skillsOffered = mutableListOf<String>()
    private val skillsWanted = mutableListOf<String>()

    private var selectedImageUri: Uri? = null

    companion object {
        const val PICK_IMAGE_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        imageProfilePhoto = findViewById(R.id.imageProfilePhoto)
        btnChangePhoto = findViewById(R.id.btnChangePhoto)
        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        cbWeekends = findViewById(R.id.cbWeekends)
        cbEvenings = findViewById(R.id.cbEvenings)
        rgVisibility = findViewById(R.id.rgVisibility)
        btnSaveProfile = findViewById(R.id.btnSaveProfile)

        layoutSkillsOffered = findViewById(R.id.layoutSkillsOffered)
        layoutSkillsWanted = findViewById(R.id.layoutSkillsWanted)
        btnAddSkillOffered = findViewById(R.id.btnAddSkillOffered)
        btnAddSkillWanted = findViewById(R.id.btnAddSkillWanted)

        btnChangePhoto.setOnClickListener { openImagePicker() }
        btnAddSkillOffered.setOnClickListener { showAddSkillDialog(true) }
        btnAddSkillWanted.setOnClickListener { showAddSkillDialog(false) }
        btnSaveProfile.setOnClickListener { saveProfile() }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imageProfilePhoto.setImageURI(selectedImageUri)
        }
    }

    private fun showAddSkillDialog(isOffered: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(if (isOffered) "Add Skill Offered" else "Add Skill Wanted")

        val input = EditText(this).apply {
            hint = "Enter skill"
            inputType = InputType.TYPE_CLASS_TEXT
            setPadding(40, 30, 40, 30)
        }

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

    private fun saveProfile() {
        val name = etName.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val availability = mutableListOf<String>()
        if (cbWeekends.isChecked) availability.add("Weekends")
        if (cbEvenings.isChecked) availability.add("Evenings")

        val visibility = when (rgVisibility.checkedRadioButtonId) {
            R.id.rbPublic -> "Public"
            R.id.rbPrivate -> "Private"
            else -> "Public"
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
            return
        }

        // Create intent to navigate to UserDetailActivity
        val intent = Intent(this, UserDetailsActivity::class.java)

        // Pass data to UserDetailActivity
        intent.putExtra("name", name)
        intent.putExtra("location", location)
        intent.putStringArrayListExtra("availability", ArrayList(availability))
        intent.putStringArrayListExtra("skillsOffered", ArrayList(skillsOffered))
        intent.putStringArrayListExtra("skillsWanted", ArrayList(skillsWanted))
        intent.putExtra("visibility", visibility)
        intent.putExtra("profileImageUri", selectedImageUri?.toString())

        startActivity(intent)
        finish()
    }
}
