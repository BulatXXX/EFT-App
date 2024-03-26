package com.example.kotlinpracticemirea.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpracticemirea.Item.Item
import com.example.kotlinpracticemirea.Item.ItemViewModel
import com.example.kotlinpracticemirea.adapters.SearchItemAdapter
import com.example.kotlinpracticemirea.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() , SearchItemAdapter.Listener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val itemViewModel: ItemViewModel by viewModels()

    var practModeIsOn = false

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
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
                    return true
                }
            }

        binding.favButton.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToNotesFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }


        //Функция очистки списка для практики
        binding.searchIcon.setOnLongClickListener {
            practModeIsOn=!practModeIsOn
            Toast.makeText(context,"Pract mode = $practModeIsOn",Toast.LENGTH_SHORT).show()
            true
        }

        binding.searchIcon.setOnClickListener {
            binding.searchBar.text.clear()
            itemViewModel.clearSearchBar()
            val imm =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken , 0)
            binding.pb.isVisible = false
        }



        itemViewModel.foundItems.observe(viewLifecycleOwner) {
            var items = it
            if (items.size > 10) {
                items = items.subList(0 , 10)
            }

            adapter.submitList(items)
            binding.pb.isVisible = false
        }



        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence? , start: Int , before: Int , count: Int) {

                if (practModeIsOn) binding.searchIcon.isVisible =
                    if (s?.length == 0) false else true

                itemViewModel.searchItem(s.toString())
                binding.pb.isVisible = true
            }

            override fun beforeTextChanged(
                s: CharSequence? ,
                start: Int ,
                count: Int ,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

    override fun OnClick(item: Item) {
        val action = SearchFragmentDirections.actionSearchFragmentToItemFragment(item)
        Navigation.findNavController(requireView()).navigate(action)
    }
/*
    private val SAVED_TEXT_TAG = "SAVED_TEXT"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_TEXT_TAG, binding.searchBar.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.searchBar.setText(savedInstanceState?.getString(SAVED_TEXT_TAG))
    }

 */
}



