package com.example.kotlinpracticemirea.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.databinding.FragmentItemBinding
import com.example.kotlinpracticemirea.databinding.FragmentSearchBinding


class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item , container , false)
    }


}