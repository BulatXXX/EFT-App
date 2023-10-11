package com.example.kotlinpracticemirea.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpracticemirea.Item
import com.example.kotlinpracticemirea.R

import com.example.kotlinpracticemirea.SearchItemAdapter
import com.example.kotlinpracticemirea.databinding.FragmentSearchBinding
import retrofit2.HttpException
import java.io.IOException


class SearchFragment : Fragment() , SearchItemAdapter.Listener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val adapter = SearchItemAdapter(this)

        binding.searchList.adapter = adapter
        binding.searchList.layoutManager =
            LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)

    }

    override fun OnClick(item: Item) {
        Toast.makeText(requireContext() , item.name , Toast.LENGTH_SHORT).show()
    }


}