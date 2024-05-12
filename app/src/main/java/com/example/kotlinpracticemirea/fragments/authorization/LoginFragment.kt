package com.example.kotlinpracticemirea.fragments.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlinpracticemirea.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var _auth: FirebaseAuth? = null
    private val auth get() = _auth!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        _auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            regBtn.setOnClickListener {
                registerUser()
            }
            logBtn.setOnClickListener {
                loginUser()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun registerUser(){
        binding.apply {
            val email = regEmail.text.toString()
            val password = regPass.text.toString()

            //Validating the info logic required
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        checkLoggedInState()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun loginUser(){
        binding.apply {
            val email = logEmail.text.toString()
            val password = logPass.text.toString()

            //Validating the info logic required
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email,password)
                    withContext(Dispatchers.Main){
                        checkLoggedInState()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null){
            Toast.makeText(requireContext(),"not logged",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(),"logged",Toast.LENGTH_SHORT).show()
        }
    }


}