package com.example.kotlinpracticemirea.fragments.authorization

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val auth: FirebaseAuth) {

    val loggedInState = MutableLiveData<Boolean>(true)
    val displayName = MutableLiveData<String>()
    fun registerUser(email: String, password: String, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    checkLoggedInState(context)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun loginUser(email: String, password: String, context: Context) {
        //Validating the info logic required
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                withContext(Dispatchers.Main) {
                    checkLoggedInState(context)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun logOutUser() {
        auth.signOut()
        loggedInState.postValue(false)
    }

    fun checkLoggedInState(context: Context) {
        if (auth.currentUser == null) {
            Toast.makeText(context, "not logged", Toast.LENGTH_SHORT).show()
            loggedInState.postValue(false)
        } else {
            Toast.makeText(context, "logged", Toast.LENGTH_SHORT).show()
            loggedInState.postValue(true)
            displayName.postValue(auth.currentUser?.displayName)
        }
    }

    fun updateUser(username: String,context: Context) {
        auth.currentUser?.let {user ->
            val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(username).build()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    user.updateProfile(userProfileChangeRequest).await()
                    displayName.postValue(username)
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show()
                    }

                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

