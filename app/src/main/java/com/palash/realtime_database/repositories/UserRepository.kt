package com.palash.realtime_database.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.palash.realtime_database.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val context: Context) {

    private val database = Firebase.database.reference

    // Write a new user
    fun addUser(user: User) {
        val userId = database.child("users").push().key // Create a unique ID for the user
        userId?.let {
            user.id = it
            database.child("users").child(it).setValue(user)
                .addOnSuccessListener {
                    showToast("User added successfully")
                }
                .addOnFailureListener {
                    showToast("Failed to add user")
                }
        }
    }

    // Read all users
    fun getUsers(): LiveData<List<User>> {
        val usersLiveData = MutableLiveData<List<User>>()
        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                snapshot.children.forEach { dataSnapshot ->
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let { userList.add(it) }
                }
                usersLiveData.value = userList
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to load users")
            }
        })
        return usersLiveData
    }

    // Update an existing user
    fun updateUser(user: User) {
        database.child("users").child(user.id.toString()).setValue(user)
            .addOnSuccessListener {
                showToast("User updated successfully")
            }
            .addOnFailureListener {
                showToast("Failed to update user")
            }
    }

    // Delete a user
    fun deleteUser(userId: String) {
        database.child("users").child(userId).removeValue()
            .addOnSuccessListener {
                showToast("User deleted successfully")
            }
            .addOnFailureListener {
                showToast("Failed to delete user")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}