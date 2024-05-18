package com.example.kotlinpracticemirea.fragments.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.databinding.FragmentProfileBinding
import com.example.kotlinpracticemirea.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            textView.text = userViewModel.displayName.value
            logoutBtn.setOnClickListener {
                userViewModel.logOutUser()
                Navigation.findNavController(requireView()).navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }

            editBtn.setOnClickListener {
                userViewModel.updateUser(profileName.text.toString(), requireContext())
            }
            settingsBtn.setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
            }

        }
        userViewModel.displayName.observe(viewLifecycleOwner) {
            binding.textView.text = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onStart() {
        super.onStart()
        checkLoggedInState()
        userViewModel.loggedInState.observe(viewLifecycleOwner){
            if (!it){
                Navigation.findNavController(requireView()).navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }
        }

    }

    private fun checkLoggedInState() {
        userViewModel.checkLoggedInState(requireContext())
    }

}