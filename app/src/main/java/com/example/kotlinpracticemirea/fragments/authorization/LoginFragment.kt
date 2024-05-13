package com.example.kotlinpracticemirea.fragments.authorization

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private val userViewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            regBtn.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            logBtn.setOnClickListener {
                loginUser()
            }
        }

        userViewModel.loggedInState.observe(viewLifecycleOwner){
            if (it){
                Navigation.findNavController(requireView())
                    .navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
        if (userViewModel.loggedInState.value == true) {
            Navigation.findNavController(requireView())
                .navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

/*
    private fun registerUser() {
        binding.apply {
            val email = regEmail.text.toString()
            val password = regPass.text.toString()

            //Validating the info logic required
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }*/

    private fun loginUser() {
        binding.apply {
            val email = logEmail.text.toString()
            val password = logPass.text.toString()

            userViewModel.loginUser(email,password,requireContext())
        }
    }

    private fun checkLoggedInState() {
        userViewModel.checkLoggedInState(requireContext())
    }


}