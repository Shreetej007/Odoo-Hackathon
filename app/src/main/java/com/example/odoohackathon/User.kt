package com.example.odoohackathon

import java.io.Serializable

data class User(
    val name: String,
    val location: String = "Unknown",
    val availability: List<String> = emptyList(),
    val skillsOffered: List<String> = emptyList(),
    val skillsWanted: List<String> = emptyList(),
    val rating: Double = 4.0,
    val isPublic: Boolean = true,
    val profileImageUrl: String? = null
) : Serializable
