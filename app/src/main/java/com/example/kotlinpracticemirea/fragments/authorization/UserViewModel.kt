package com.example.kotlinpracticemirea.fragments.authorization

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) :ViewModel()  {

    val loggedInState = repository.loggedInState
    val displayName = repository.displayName

    fun registerUser(email: String, password: String, context: Context){
        repository.registerUser(email, password, context)

    }

    fun loginUser(email: String, password: String, context: Context){
        repository.loginUser(email, password, context)
    }

    fun checkLoggedInState(context: Context){
        repository.checkLoggedInState(context)
    }

    fun logOutUser(){
        repository.logOutUser()
    }

    fun updateUser(username: String,context: Context){
        repository.updateUser(username, context)
    }
}