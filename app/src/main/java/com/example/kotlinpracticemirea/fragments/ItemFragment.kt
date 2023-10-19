package com.example.kotlinpracticemirea.fragments


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.kotlinpracticemirea.Item
import com.example.kotlinpracticemirea.ItemViewModel
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.databinding.FragmentItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private val args: ItemFragmentArgs by navArgs()
    private val itemViewModel: ItemViewModel by viewModels()
    private var isFavourite = false

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentItemBinding.inflate(layoutInflater , container , false)


        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)


        itemViewModel.check(args.item.id) {
            setUpLike(it);
            isFavourite = it
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        binding.likeButton.setOnClickListener {
            if (!isFavourite) {
                binding.likeButton.setImageResource(R.drawable.like_red)
                isFavourite = !isFavourite
                itemViewModel.addToFavourites(args.item)
            }else{
                itemViewModel.deleteFromFavourites(args.item)
                isFavourite = !isFavourite
                binding.likeButton.setImageResource(R.drawable.like_white)
            }
        }
        binding.itemImage.setOnClickListener {
                
        }

        /*itemViewModel.selectedItem.observe(viewLifecycleOwner){
            if (it==args.item.id){
                binding.likeButton.setImageResource(R.drawable.like_red)
                isFavourite = !isFavourite
            }else{
                binding.likeButton.setImageResource(R.drawable.like_white)
                isFavourite = !isFavourite
            }
        }*/
        //checkIsFavourite(args.item.id)
        setUpInterface(args.item)




    }


    private fun setUpLike(isFavourite: Boolean) {
        if(isFavourite){
            binding.likeButton.setImageResource(R.drawable.like_red)
        }
        else{
            binding.likeButton.setImageResource(R.drawable.like_white)
        }
    }

    private fun checkIsFavourite(id: String?) {
        if (id != null) {
            itemViewModel.sendCheckIsFavourite(id)
        }

    }

    private fun setUpInterface(item: Item) {
        binding.pb.isVisible=true
        binding.itemName.text = item.name
        binding.itemDescription.text = item.description



        val price = item.avg24hPrice
        if (price == 0) {
            binding.itemPrice.text = "Item can't be bought at a flea market!"
            binding.itemPrice.setTextColor(Color.RED)
        } else binding.itemPrice.text = price.toString() + " ROUBLES"
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        val fixedThreadPoolDispatcher =
            newSingleThreadContext ( "Network")
        CoroutineScope(fixedThreadPoolDispatcher).launch {
            val imageUrl = item.image512pxLink
            try {
                val inputStream = java.net.URL(imageUrl).openStream()
                image = BitmapFactory.decodeStream(inputStream)
                handler.post {
                    binding.itemImage.setImageBitmap(image)
                    binding.pb.isVisible =false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            fixedThreadPoolDispatcher.close()
        }


    }


}