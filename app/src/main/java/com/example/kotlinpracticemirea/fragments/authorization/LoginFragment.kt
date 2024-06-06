package com.example.kotlinpracticemirea.fragments.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.kotlinpracticemirea.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

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
                Navigation.findNavController(requireView())
                    .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            logBtn.setOnClickListener {
                loginUser()
            }
        }
        userViewModel.loggedInState.observe(viewLifecycleOwner){
            if (it) Navigation.findNavController(requireView()).popBackStack()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun loginUser() {
        binding.apply {
            val email = logEmail.text.toString()
            val password = logPass.text.toString()
            userViewModel.loginUser(email, password, requireContext())
        }
    }


}