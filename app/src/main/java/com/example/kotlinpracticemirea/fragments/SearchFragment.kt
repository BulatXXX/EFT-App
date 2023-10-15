package com.example.kotlinpracticemirea.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpracticemirea.Item
import com.example.kotlinpracticemirea.ItemViewModel

import com.example.kotlinpracticemirea.adapters.SearchItemAdapter
import com.example.kotlinpracticemirea.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() , SearchItemAdapter.Listener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val itemViewModel:ItemViewModel by viewModels()

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
            object : LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }



        itemViewModel.foundItems.observe(viewLifecycleOwner){
            var items = it
            if(items.size>=8){
                items=items.subList(0,8)
            }
            adapter.submitList(items)
            binding.pb.isVisible = false
        }
        binding.searchBar.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence? ,
                start: Int ,
                count: Int ,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence? , start: Int , before: Int , count: Int) {
                itemViewModel.searchItem(s.toString())
                binding.pb.isVisible = true
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


    }

    override fun OnClick(item: Item) {
        val action = SearchFragmentDirections.actionSearchFragmentToItemFragment(item)
        Navigation.findNavController(requireView()).navigate(action)
    }


}