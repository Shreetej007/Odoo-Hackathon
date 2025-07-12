package com.example.odoohackathon

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RatingFeedbackActivity : AppCompatActivity() {

    private lateinit var tvSwapPartnerName: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var etFeedback: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnCancel: Button

    // For demo, you can pass partner name as Intent extra with key "partner_name"
    private var partnerName: String = "Your Swap Partner"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating_feedback)

        tvSwapPartnerName = findViewById(R.id.tvSwapPartnerName)
        ratingBar = findViewById(R.id.ratingBar)
        etFeedback = findViewById(R.id.etFeedback)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnCancel = findViewById(R.id.btnCancel)

        // Get partner name from intent if available
        partnerName = intent.getStringExtra("partner_name") ?: partnerName
        tvSwapPartnerName.text = "Rate your swap with $partnerName"

        btnCancel.setOnClickListener {
            finish() // close activity without saving
        }

        btnSubmit.setOnClickListener {
            val rating = ratingBar.rating.toInt()
            val feedback = etFeedback.text.toString().trim()

            if (rating == 0) {
                Toast.makeText(this, "Please provide a rating", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (feedback.isEmpty()) {
                Toast.makeText(this, "Please provide feedback", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Save rating and feedback to database or send to server

            Toast.makeText(this, "Thanks for your feedback!", Toast.LENGTH_LONG).show()
            finish() // close activity after submitting
        }
    }
}
