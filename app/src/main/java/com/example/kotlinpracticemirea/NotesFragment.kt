package com.example.kotlinpracticemirea


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kotlinpracticemirea.databinding.AddNoteCustomDialogBinding
import com.example.kotlinpracticemirea.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    val fleaMarketItemViewModel : FleaMarketItemViewModel by viewModels()

    private val items : ArrayList<FleaMarketItem> = ArrayList()
    private var adapter = FleaMarketItemAdapter()
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


        fleaMarketItemViewModel.items.observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.rv.adapter = adapter
        }
        binding.rv.layoutManager =
            LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        fleaMarketItemViewModel.items
        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }



        var selectedIcon: Int = R.drawable.weapon_icon_w
        binding.noteButton.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)

            val dialogBinding = AddNoteCustomDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialogBinding.cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialogBinding.saveBtn.setOnClickListener {
                with(dialogBinding){
                    val item = FleaMarketItem(id = null, nameEt.text.toString(),price.text.toString(), img = selectedIcon)
                    println("BOOBS $item")

                    /*
                    CoroutineScope(Dispatchers.IO).launch {
                        db.fleaMarketDao().addItem(item)
                    }

                     */

                    dialog.dismiss()
                }


            }
            with(dialogBinding){
                weaponIc.setOnClickListener {
                    selectedIcon = R.drawable.weapon_icon_w
                }
                plusIc.setOnClickListener {
                    selectedIcon = R.drawable.icon_plus_sign_w
                }
                ammoIc.setOnClickListener {
                    selectedIcon = R.drawable.icon_ammunition__w
                }
                foodIc.setOnClickListener {
                    selectedIcon = R.drawable.food_icon_w
                }
            }
            dialog.show()
        }
    }


}