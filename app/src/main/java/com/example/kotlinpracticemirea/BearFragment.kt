package com.example.kotlinpracticemirea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinpracticemirea.databinding.FragmentBearBinding


class BearFragment : Fragment() {
    private var _binding: FragmentBearBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBearBinding.inflate(inflater,container,false)
        return binding.root
    }


}