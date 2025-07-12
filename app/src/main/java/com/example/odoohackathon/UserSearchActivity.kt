package com.example.odoohackathon

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserSearchActivity : AppCompatActivity() {



    private lateinit var etSearchSkill: EditText
    private lateinit var rvUsers: RecyclerView
    private lateinit var userAdapter: UserAdapter

    private val allUsers = listOf(
        User("Aarav", "Mumbai", listOf("Weekends"), listOf("Photoshop", "Excel"), listOf("Java", "Kotlin"), 4.3),
        User("Ishita", "Delhi", listOf("Evenings"), listOf("Excel", "PowerPoint"), listOf("Graphic Design"), 4.0),
        User("Vihaan", "Bangalore", listOf("Weekends", "Evenings"), listOf("Java", "Kotlin"), listOf("UI Design"), 4.5),
        User("Saanvi", "Hyderabad", listOf("Weekends"), listOf("Photoshop", "Illustrator"), listOf("React"), 3.9),
        User("Aryan", "Pune", listOf("Evenings"), listOf("Python", "SQL"), listOf("ML", "DSA"), 4.1),
        User("Meera", "Chennai", listOf("Weekends", "Evenings"), listOf("Excel", "Data Analysis"), listOf("Excel Macros"), 4.6),
        User("Devansh", "Ahmedabad", listOf("Weekends"), listOf("React", "HTML"), listOf("Firebase", "MongoDB"), 4.2),
        User("Riya", "Jaipur", listOf("Evenings"), listOf("Photography", "Canva"), listOf("Editing"), 3.8),
        User("Krishna", "Kolkata", listOf("Weekends"), listOf("Video Editing", "Premiere Pro"), listOf("After Effects"), 4.4),
        User("Ananya", "Lucknow", listOf("Evenings"), listOf("Graphic Design", "CorelDraw"), listOf("HTML"), 4.0),
        User("Yash", "Bhopal", listOf("Weekends"), listOf("Android", "Java"), listOf("Kotlin"), 4.5),
        User("Tanya", "Surat", listOf("Evenings"), listOf("Python", "Machine Learning"), listOf("Power BI"), 4.7),
        User("Kabir", "Nagpur", listOf("Weekends"), listOf("Node.js", "MongoDB"), listOf("React"), 3.9),
        User("Sneha", "Indore", listOf("Evenings"), listOf("Sketching", "Painting"), listOf("Digital Art"), 4.2),
        User("Rudra", "Patna", listOf("Weekends", "Evenings"), listOf("AutoCAD", "Revit"), listOf("3DS Max"), 4.3),
        User("Pooja", "Thane", listOf("Weekends"), listOf("Illustrator", "Canva"), listOf("Photoshop"), 3.7),
        User("Aditya", "Noida", listOf("Evenings"), listOf("Flutter", "Dart"), listOf("Firebase"), 4.6),
        User("Kavya", "Faridabad", listOf("Weekends"), listOf("Public Speaking", "Content Writing"), listOf("Blogging"), 4.1),
        User("Manav", "Rajkot", listOf("Evenings"), listOf("Excel", "Finance"), listOf("PowerPoint"), 4.0),
        User("Priya", "Visakhapatnam", listOf("Weekends", "Evenings"), listOf("Video Editing", "Filmora"), listOf("Vlogging"), 4.3),
        User("Nikhil", "Chandigarh", listOf("Weekends"), listOf("HTML", "CSS"), listOf("JavaScript"), 3.9),
        User("Shreya", "Vadodara", listOf("Evenings"), listOf("Figma", "UI/UX Design"), listOf("Mobile Prototyping"), 4.4),
        User("Om", "Amritsar", listOf("Weekends"), listOf("Photoshop", "Photography"), listOf("Lightroom"), 4.0),
        User("Divya", "Coimbatore", listOf("Evenings"), listOf("Excel", "Power BI"), listOf("Data Studio"), 4.2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        etSearchSkill = findViewById(R.id.etSearchSkill)
        rvUsers = findViewById(R.id.rvUsers)

        userAdapter = UserAdapter(allUsers)
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = userAdapter

        etSearchSkill.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s.toString().trim().lowercase()
                if (q.isEmpty()) {
                    userAdapter.updateList(allUsers)
                } else {
                    userAdapter.updateList(allUsers.filter {
                        it.skillsOffered.any { sk -> sk.lowercase().contains(q) }
                    })
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){}
        })
    }
}
