package com.singularity.eft_app.fragments


import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.singularity.eft_app.Item.Item
import com.singularity.eft_app.Item.ItemViewModel
import com.singularity.eft_app.R
import com.singularity.eft_app.databinding.FragmentItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

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
            } else {
                itemViewModel.deleteFromFavourites(args.item)
                isFavourite = !isFavourite
                binding.likeButton.setImageResource(R.drawable.like_white)
            }
        }
        binding.itemImage.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Saving image")
            builder.setMessage("Do you want to save image?")
            builder.setPositiveButton("Yes") { _, i ->
                saveToGallery(requireContext(), binding.itemImage.drawToBitmap())
            }
            builder.show()
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


    private fun setUpInterface(item: Item) {
        binding.pb.isVisible = true
        binding.itemName.text = item.name
        binding.itemDescription.text = item.description

        val price = item.avg24hPrice
        if (price == 0) {
            binding.itemPrice.text = "Item can't be bought at a flea market!"
            binding.itemPrice.setTextColor(Color.RED)
        } else binding.itemPrice.text = price.toString() + " ROUBLES"
        loadImage(item)


    }

    private fun loadImage(item: Item) {
        binding.pb.isVisible = true
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        val dispatcher =
            newSingleThreadContext("Network")
        CoroutineScope(dispatcher).launch {
            val imageUrl = item.image512pxLink
            try {
                val inputStream = URL(imageUrl).openStream()
                image = BitmapFactory.decodeStream(inputStream)
                handler.post {
                    binding.itemImage.setImageBitmap(image)
                    binding.itemImage.isVisible = true
                    binding.pb.isVisible = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            dispatcher.close()
        }
    }

    private fun saveToGallery(context: Context, bitmap: Bitmap, albumName: String = "Tarkov_App") {
        val dispatcher =
            newSingleThreadContext("Disk")
        CoroutineScope(dispatcher).launch {
            val filename = "${System.currentTimeMillis()}.png"
            val write: (OutputStream) -> Boolean = {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        "${Environment.DIRECTORY_DCIM}/$albumName"
                    )
                }

                context.contentResolver.let {
                    it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                        ?.let { uri ->
                            it.openOutputStream(uri)?.let(write)
                        }
                }
            } else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        .toString() + File.separator + albumName
                val file = File(imagesDir)
                if (!file.exists()) {
                    file.mkdir()
                }
                val image = File(imagesDir, filename)
                write(FileOutputStream(image))
            }
        }
        dispatcher.close()
    }


}