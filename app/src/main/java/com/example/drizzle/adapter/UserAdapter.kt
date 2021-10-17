package com.example.drizzle.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drizzle.R
import com.example.drizzle.model.User
import com.example.drizzle.ui.ChatActivity
import com.example.drizzle.ui.UserDetails
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_layout.view.*

class UserAdapter(val context: Context,val list: ArrayList<User>)
    :RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = list[position]
        holder.itemView.apply {
            textName.text = data.name.toString()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", data.name)
            intent.putExtra("uid", data.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }
}