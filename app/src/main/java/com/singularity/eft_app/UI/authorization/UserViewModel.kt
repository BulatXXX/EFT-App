package com.singularity.eft_app.UI.authorization

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    val loggedInState = repository.loggedInState
    val displayName = repository.displayName
    val profilePic = repository.profilePic


    fun registerUser(username:String,email: String, password: String, context: Context) {
        repository.registerUser(username,email, password, context)

    }

    fun loginUser(email: String, password: String, context: Context) {
        repository.loginUser(email, password, context)
    }

    fun checkLoggedInState(context: Context) {
        repository.checkLoggedInState(context)
    }

    fun logOutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.logOutUser()
        }
    }

    fun updateUser(context: Context,username: String = displayName.value.toString(),uri: Uri?=null) {
        repository.updateUser(username, context,uri)
    }
}