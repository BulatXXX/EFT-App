package com.example.kotlinpracticemirea.fragments


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpracticemirea.room.FleaMarketItem
import com.example.kotlinpracticemirea.FleaMarketItemAdapter
import com.example.kotlinpracticemirea.FleaMarketItemViewModel
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.databinding.AddNoteCustomDialogBinding
import com.example.kotlinpracticemirea.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    val fleaMarketItemViewModel: FleaMarketItemViewModel by viewModels()

    private val items: ArrayList<FleaMarketItem> = ArrayList()
    private var adapter = FleaMarketItemAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = adapter


        fleaMarketItemViewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.rv.adapter = adapter
        }
        binding.rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fleaMarketItemViewModel.items
        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }


        var selectedIcon: Int? = null
        binding.noteButton.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)

            val dialogBinding = AddNoteCustomDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialogBinding.cancelBtn.setOnClickListener {
                selectedIcon = null
                dialog.dismiss()
            }
            dialogBinding.saveBtn.setOnClickListener {
                with(dialogBinding) {
                    val name = nameEt.text.toString()
                    val price = price.text.toString()
                    if (selectedIcon == null) {
                        Toast.makeText(requireContext(), "Choose an icon", Toast.LENGTH_SHORT)
                            .show()
                    } else if (name.isEmpty()) {
                        Toast.makeText(requireContext(), "Enter item name", Toast.LENGTH_SHORT)
                            .show()
                    } else if (price.isEmpty()) {
                        Toast.makeText(requireContext(), "Enter item price", Toast.LENGTH_SHORT)
                            .show()
                    } else if (!price.isDigitsOnly()) {
                        Toast.makeText(
                            requireContext(),
                            "Price must be a digit",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val item = FleaMarketItem(
                            id = null,
                            name,
                            price,
                            img = selectedIcon!!
                        )
                        fleaMarketItemViewModel.addItem(item)
                        selectedIcon = null
                        dialog.dismiss()

                    }
                }


            }
            with(dialogBinding) {
                weaponIc.setOnClickListener {
                    updateIcons(selectedIcon, dialogBinding)
                    selectedIcon = R.drawable.weapon_icon_w
                    weaponIc.setImageResource(R.drawable.weapon_icon_b)
                }
                plusIc.setOnClickListener {
                    updateIcons(selectedIcon, dialogBinding)
                    selectedIcon = R.drawable.icon_plus_sign_w
                    plusIc.setImageResource(R.drawable.icon_plus_sign_b)
                }
                ammoIc.setOnClickListener {
                    updateIcons(selectedIcon, dialogBinding)
                    selectedIcon = R.drawable.icon_ammunition__w
                    ammoIc.setImageResource(R.drawable.icon_ammunition__b)
                }
                foodIc.setOnClickListener {
                    updateIcons(selectedIcon, dialogBinding)
                    selectedIcon = R.drawable.food_icon_w
                    foodIc.setImageResource(R.drawable.food_icon_b)
                }
            }
            dialog.show()
        }
    }

    private fun updateIcons(selectedIcon: Int?, dialogBinding: AddNoteCustomDialogBinding) {
        when (selectedIcon) {
            R.drawable.weapon_icon_w -> {
                dialogBinding.weaponIc.setImageResource(R.drawable.weapon_icon_w)
            }
            R.drawable.icon_plus_sign_w -> {
                dialogBinding.plusIc.setImageResource(R.drawable.icon_plus_sign_w)
            }
            R.drawable.icon_ammunition__w -> {
                dialogBinding.ammoIc.setImageResource(R.drawable.icon_ammunition__w)
            }
            R.drawable.food_icon_w -> {
                dialogBinding.foodIc.setImageResource(R.drawable.food_icon_w)
            }
            else->{

            }
        }

    }


}