package com.singularity.eft_app.UI

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.singularity.eft_app.Item.Item
import com.singularity.eft_app.Item.ItemViewModel
import com.singularity.eft_app.R
import com.singularity.eft_app.databinding.FragmentItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private val args: ItemFragmentArgs by navArgs()
    private val itemViewModel: ItemViewModel by viewModels()
    private var isFavourite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemViewModel.check(args.item.id) {
            setUpLike(it)
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
            } else {
                itemViewModel.deleteFromFavourites(args.item)
                isFavourite = !isFavourite
                binding.likeButton.setImageResource(R.drawable.like_white)
            }
        }
        setUpInterface(args.item)
    }

    private fun setUpLike(isFavourite: Boolean) {
        if (isFavourite) {
            binding.likeButton.setImageResource(R.drawable.like_red)
        } else {
            binding.likeButton.setImageResource(R.drawable.like_white)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpInterface(item: Item) {
        binding.pb.isVisible = true
        binding.itemName.text = item.name
        binding.itemDescription.text = item.description

        val price = item.avg24hPrice
        if (price == 0) {
            binding.itemPrice.text = "Item can't be bought at a flea market!"
            binding.itemPrice.setTextColor(Color.RED)
        } else binding.itemPrice.text =  "$price ROUBLES"
        loadImage(item)
    }

    private fun loadImage(item: Item) {
        binding.pb.isVisible = true
        Glide.with(requireContext()).load(item.image512pxLink).listener(object: RequestListener<Drawable>{

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                binding.pb.isVisible = true
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                binding.pb.isVisible = false
                binding.itemImage.isVisible = true
                return false
            }

        }).into(binding.itemImage)
        binding.pb.isVisible = false
        binding.itemImage.isVisible = true
    }
}