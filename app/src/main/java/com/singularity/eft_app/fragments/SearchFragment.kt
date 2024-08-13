package com.singularity.eft_app.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.singularity.eft_app.Item.Item
import com.singularity.eft_app.Item.ItemViewModel
import com.singularity.eft_app.Item.SearchFragmentState
import com.singularity.eft_app.R
import com.singularity.eft_app.adapters.SearchItemAdapter
import com.singularity.eft_app.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchItemAdapter.Listener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val itemViewModel: ItemViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchItemAdapter(this)
        // itemViewModel.clearSearchHistory(requireContext())


        itemViewModel.searchFragmentState.observe(viewLifecycleOwner) {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                SearchFragmentState.IS_SHOWING_HISTORY -> {

                    // itemViewModel.getHistoryList(requireContext())
                    adapter.submitList(itemViewModel.searchHistoryList.value)
                    binding.searchIcon.setImageResource(R.drawable.trash)
                }

                SearchFragmentState.IS_SEARCHING -> {

                    binding.searchIcon.setImageResource(R.drawable.search)
                    binding.pb.isVisible = true
                    binding.refreshBtn.isVisible = false
                    binding.noItemsFoundTV.isVisible = false
                }

                SearchFragmentState.IS_SHOWING_SEARCH_RESULT -> {
                    adapter.submitList(itemViewModel.foundItems.value)
                    binding.searchIcon.setImageResource(R.drawable.search)
                }

                SearchFragmentState.IS_IDLE -> {
                    adapter.submitList(emptyList())

                    binding.searchIcon.setImageResource(R.drawable.search)
                }
            }
        }

        itemViewModel.searchHistoryList.observe(viewLifecycleOwner) {
            if (itemViewModel.searchFragmentState.value == SearchFragmentState.IS_SHOWING_HISTORY) {
                adapter.submitList(itemViewModel.searchHistoryList.value)
            }
        }



        binding.searchBar.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {

                if (v.isFocused) {
                    val inputMethodManager =
                        v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)

                    itemViewModel.searchFragmentState.value = SearchFragmentState.IS_IDLE

                    v.clearFocus()
                } else {
                    if (binding.searchBar.text.isEmpty())
                        itemViewModel.searchFragmentState.value =
                            SearchFragmentState.IS_SHOWING_HISTORY

                }
            }
            false
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length != 0) {
                    itemViewModel.searchItem(binding.searchBar.text.toString())
                    itemViewModel.searchFragmentState.value = SearchFragmentState.IS_SEARCHING

                } else {
                    itemViewModel.searchJob?.cancel()
                    binding.pb.isVisible = false
                    binding.noItemsFoundTV.isVisible = false
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchList.adapter = adapter

        binding.searchList.layoutManager =
            object : LinearLayoutManager(context, VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }

        binding.searchIcon.setOnClickListener {
            if (itemViewModel.searchFragmentState.value == SearchFragmentState.IS_SHOWING_HISTORY) {
                Toast.makeText(requireContext(), "Search History is deleted!", Toast.LENGTH_SHORT)
                    .show()
                itemViewModel.searchFragmentState.value = SearchFragmentState.IS_IDLE

            } else {
                binding.searchBar.text.clear()
                itemViewModel.clearSearchBar()
                val imm =
                    it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                binding.pb.isVisible = false
            }
        }

        itemViewModel.foundItems.observe(viewLifecycleOwner) {
            var items = it
            if (items.size > 10) {
                items = items.subList(0, 10)
                binding.noItemsFoundTV.isVisible = false
            }
            if (items.isEmpty() && binding.searchBar.text.isNotEmpty()) {
                binding.noItemsFoundTV.isVisible = true
            }
            adapter.submitList(items)
            binding.pb.isVisible = false
        }

        binding.refreshBtn.setOnClickListener {
            itemViewModel.searchItem(binding.searchBar.text.toString())
            itemViewModel.searchFragmentState.value = SearchFragmentState.IS_SEARCHING
        }

        itemViewModel.isResponseSuccessful.observe(viewLifecycleOwner) {
            if (!it) {
                binding.refreshBtn.isVisible = true
                binding.pb.isVisible = false
                adapter.submitList(emptyList())
            }
        }
    }


    override fun OnClick(item: Item) {
        // itemViewModel.saveToSharedPreferences(item , requireContext())
        itemViewModel.searchFragmentState.value = SearchFragmentState.IS_SHOWING_SEARCH_RESULT
        val action = SearchFragmentDirections.actionSearchFragmentToItemFragment(item)
        Navigation.findNavController(requireView()).navigate(action)
    }
}



