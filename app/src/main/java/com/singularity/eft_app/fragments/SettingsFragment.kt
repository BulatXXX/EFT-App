package com.singularity.eft_app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.singularity.eft_app.FleaApplication
import com.singularity.eft_app.R
import com.singularity.eft_app.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.switchTheme.setOnCheckedChangeListener { switcher, checked ->
            (requireActivity().applicationContext as FleaApplication).switchTheme(checked)
            requireActivity().recreate()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        initInterface()
        return binding.root
    }

    private fun initInterface() {
        if ((requireActivity().applicationContext as FleaApplication).darkTheme) {
            binding.imageView.setImageResource(
                R.drawable.moon
            )
            binding.backBtn.setOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
            binding.switchTheme.text = "Dark Theme"
            binding.switchTheme.isChecked = true
        } else {
            binding.imageView.setImageResource(R.drawable.sun)
            binding.switchTheme.text = "Light Theme"
            binding.switchTheme.isChecked = false
        }

    }
}