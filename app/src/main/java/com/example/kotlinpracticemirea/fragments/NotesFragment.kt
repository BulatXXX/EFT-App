package com.example.kotlinpracticemirea.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpracticemirea.Item.Item
import com.example.kotlinpracticemirea.Item.ItemViewModel
import com.example.kotlinpracticemirea.adapters.FleaMarketItemAdapter
import com.example.kotlinpracticemirea.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() , FleaMarketItemAdapter.Listener {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!


    private val itemViewModel: ItemViewModel by viewModels()
    private val adapter = FleaMarketItemAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(inflater , container , false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        binding.rv.adapter = adapter
        binding.rv.layoutManager =
            LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
        itemViewModel.favouriteItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.rv.adapter = adapter
        }

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

    }

    override fun OnClick(item: Item) {
        val action = NotesFragmentDirections.actionNotesFragmentToItemFragment(item)
        Navigation.findNavController(requireView()).navigate(action)
    }
}