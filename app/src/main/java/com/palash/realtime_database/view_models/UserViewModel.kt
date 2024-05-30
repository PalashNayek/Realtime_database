package com.palash.realtime_database.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.palash.realtime_database.models.User
import com.palash.realtime_database.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val users: LiveData<List<User>> = userRepository.getUsers()

    fun addUser(user: User) {
        userRepository.addUser(user)
    }

    fun updateUser(user: User) {
        userRepository.updateUser(user)
    }

    fun deleteUser(userId: String) {
        userRepository.deleteUser(userId)
    }
}