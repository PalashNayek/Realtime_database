package com.palash.realtime_database.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.palash.realtime_database.R
import com.palash.realtime_database.models.User

class UserListAdapter(private val deleteClick: (User) -> Unit, private val itemClick: (User) -> Unit) : ListAdapter<User, UserListAdapter.UserViewHolder>(DiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.users_item_view, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, deleteClick, itemClick)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userName = view.findViewById<TextView>(R.id.txt_user_name)
        val userEmail = view.findViewById<TextView>(R.id.txt_email)
        val userDelete = view.findViewById<ImageView>(R.id.itemDelete)
        val rootItem = view.findViewById<ConstraintLayout>(R.id.root)



        fun bind(item: User, deleteClick: (User) -> Unit, itemClick: (User) -> Unit) {
            userName.text = item.name
            userEmail.text = item.email
            userDelete.setOnClickListener {
                deleteClick(item)
            }

            rootItem.setOnClickListener {
                itemClick(item)
            }

        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }

    }

}