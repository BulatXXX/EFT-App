package com.example.kotlinpracticemirea.fragments.authorization

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.kotlinpracticemirea.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream

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
                Navigation.findNavController(requireView())
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }
            profileName.hint = userViewModel.displayName.value

            editBtn.setOnClickListener {
                userViewModel.updateUser(requireContext(), profileName.text.toString())
            }
            settingsBtn.setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
            }


            profilePic.apply {
                setOnClickListener {
                    chooseProfilePic()
                }
            }

        }
        userViewModel.profilePic.observe(viewLifecycleOwner) {
            if (it != null) {
              binding.profilePic.setImageURI(it)
            }
        }
        userViewModel.displayName.observe(viewLifecycleOwner) {
            binding.textView.text = it
        }
    }

    private fun chooseProfilePic() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            userViewModel.updateUser(requireContext(), uri = imageUri)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
        userViewModel.loggedInState.observe(viewLifecycleOwner) {
            if (!it) {
                Navigation.findNavController(requireView())
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }
        }

    }

    private fun checkLoggedInState() {
        userViewModel.checkLoggedInState(requireContext())
    }

}