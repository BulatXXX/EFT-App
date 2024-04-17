package com.example.kotlinpracticemirea.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.kotlinpracticemirea.Item.Item
import com.example.kotlinpracticemirea.Item.ItemViewModel
import com.example.kotlinpracticemirea.Item.SearchFragmentState
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.adapters.SearchItemAdapter
import com.example.kotlinpracticemirea.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() , SearchItemAdapter.Listener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val itemViewModel: ItemViewModel by viewModels()

    var practModeIsOn = false

    private val SEARCH_DEBOUNCE_DELAY = 2000L
    private val searchRunnable = Runnable {  itemViewModel.searchItem(binding.searchBar.text.toString()) }


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    @SuppressLint("SetTextI18n" , "ClickableViewAccessibility")
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        val adapter = SearchItemAdapter(this)
        // itemViewModel.clearSearchHistory(requireContext())



        itemViewModel.searchFragmentState.observe(viewLifecycleOwner) {
            when (it) {
                SearchFragmentState.IS_SHOWING_HISTORY -> {
                    itemViewModel.getHistoryList(requireContext())
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

        itemViewModel.searchHistoryList.observe(viewLifecycleOwner){
            if (itemViewModel.searchFragmentState.value == SearchFragmentState.IS_SHOWING_HISTORY){
                adapter.submitList(itemViewModel.searchHistoryList.value)
            }
        }



        binding.searchBar.setOnTouchListener { view , event ->
            if (event.action == MotionEvent.ACTION_UP) {

                if (view.isFocused) {
                    val inputMethodManager =
                        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken , 0)

                    itemViewModel.searchFragmentState.value = SearchFragmentState.IS_IDLE

                    view.clearFocus()
                } else {
                    if(binding.searchBar.text.isEmpty()) itemViewModel.searchFragmentState.value = SearchFragmentState.IS_SHOWING_HISTORY

                }
            }
            false // Возвращаем false, чтобы позволить другим обработчикам событий обрабатывать событие
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence? , start: Int , before: Int , count: Int) {

                if (practModeIsOn) binding.searchIcon.isVisible =
                    if (s?.length == 0) false else true

                if (s?.length != 0) {
                    searchDebounce()

                    itemViewModel.searchFragmentState.value = SearchFragmentState.IS_SEARCHING


                } else {
                    itemViewModel.searchJob?.cancel()
                    binding.pb.isVisible = false
                    binding.noItemsFoundTV.isVisible = false

                    itemViewModel.searchFragmentState.value = SearchFragmentState.IS_SHOWING_HISTORY
                }
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
            practModeIsOn = !practModeIsOn
            Toast.makeText(context , "Pract mode = $practModeIsOn" , Toast.LENGTH_SHORT).show()
            true
        }

        binding.searchIcon.setOnClickListener {
            if (itemViewModel.searchFragmentState.value == SearchFragmentState.IS_SHOWING_HISTORY) {

                itemViewModel.clearSearchHistory(requireContext())
                Toast.makeText(requireContext(),"Search History is deleted!",Toast.LENGTH_SHORT).show()
                itemViewModel.searchFragmentState.value = SearchFragmentState.IS_IDLE

            }
            else {
                binding.searchBar.text.clear()
                itemViewModel.clearSearchBar()
                val imm =
                    it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken , 0)
                binding.pb.isVisible = false
            }
        }

        itemViewModel.foundItems.observe(viewLifecycleOwner) {
            var items = it
            if (items.size > 10) {
                items = items.subList(0 , 10)
                binding.noItemsFoundTV.isVisible = false
            }
            if (items.isEmpty() && binding.searchBar.text.isNotEmpty()) {
                binding.noItemsFoundTV.isVisible = true
            }

            adapter.submitList(items)
            binding.pb.isVisible = false

        }

        binding.favButton.setOnLongClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToSettingsFragment()
            Navigation.findNavController(requireView()).navigate(action)
            true
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

    private fun searchDebounce() {
        val handler = Handler(Looper.getMainLooper())
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable,SEARCH_DEBOUNCE_DELAY)

    }


    override fun OnClick(item: Item) {
        itemViewModel.saveToSharedPreferences(item , requireContext())

        itemViewModel.searchFragmentState.value = SearchFragmentState.IS_SHOWING_SEARCH_RESULT

        val action = SearchFragmentDirections.actionSearchFragmentToItemFragment(item)
        Navigation.findNavController(requireView()).navigate(action)
    }

}



