package com.singularity.eft_app.fragments.authorization

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val auth: FirebaseAuth) {

    val loggedInState = MutableLiveData<Boolean>(true)
    val displayName = MutableLiveData<String>()
    val profilePic = MutableLiveData<Uri>()


    fun registerUser(username: String, email: String, password: String, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.apply {
                    createUserWithEmailAndPassword(email, password).await()
                    updateUser(username, context)
                }
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
                auth.signInWithEmailAndPassword(email, password).await()
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

    suspend fun logOutUser() {
        auth.signOut()
        loggedInState.postValue(false)
        delay(300)
    }

    fun checkLoggedInState(context: Context) {
        if (auth.currentUser == null) {
            Toast.makeText(context, "not logged", Toast.LENGTH_SHORT).show()
            loggedInState.postValue(false)

        } else {
            Toast.makeText(context, "logged", Toast.LENGTH_SHORT).show()
            loggedInState.postValue(true)
            displayName.postValue(auth.currentUser?.displayName)
          //  profilePic.postValue(auth.currentUser?.photoUrl)
        }
    }




    fun updateUser(username: String, context: Context, uri: Uri? = null) {
        auth.currentUser?.let { user ->
            val userProfileChangeRequest =
                UserProfileChangeRequest.Builder()
                    .setDisplayName(username).setPhotoUri(uri)
                    .build()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    user.updateProfile(userProfileChangeRequest).await()
                    username.let {displayName.postValue(it)  }
                    uri.let { profilePic.postValue(it) }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

