package com.example.odoohackathon

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private var users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        val tvSkillsOffered: TextView = view.findViewById(R.id.tvSkillsOffered)
        val tvSkillsWanted: TextView = view.findViewById(R.id.tvSkillsWanted)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val btnRequest: Button = view.findViewById(R.id.btnRequest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_card, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.tvName.text = user.name
        holder.tvLocation.text = user.location
        holder.tvSkillsOffered.text = "Skills Offered: ${user.skillsOffered.joinToString(", ")}"
        holder.tvSkillsWanted.text = "Skills Wanted: ${user.skillsWanted.joinToString(", ")}"
        holder.tvRating.text = "${user.rating}/5"

        holder.btnRequest.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Login required to request", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, LoginActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateList(filteredUsers: List<User>) {
        users = filteredUsers
        notifyDataSetChanged()
    }
}
