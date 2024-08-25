package com.singularity.eft_app.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.singularity.eft_app.Item.Item
import com.singularity.eft_app.Item.ItemViewModel
import com.singularity.eft_app.UI.adapters.FleaMarketItemAdapter
import com.singularity.eft_app.databinding.FragmentNotesBinding
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
    ): View {
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
    }

    override fun onClick(item: Item) {
        val action = NotesFragmentDirections.actionNotesFragmentToItemFragment(item)
        Navigation.findNavController(requireView()).navigate(action)
    }
}