package com.example.kotlinpracticemirea.fragments

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import com.example.kotlinpracticemirea.databinding.FragmentLinkBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class LinkFragment : Fragment() {

    private var _binding: FragmentLinkBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLinkBinding.inflate(layoutInflater , container , false)

        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        binding.loadButton.setOnClickListener {
            val link = binding.link.text.toString()
            if (link.isNotEmpty()) {
                loadImage(link)
            } else {
                Toast.makeText(requireContext() , "Wrong link! Try another!" , Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.saveButton.setOnClickListener {
            saveToGallery(requireContext() , binding.image.drawToBitmap())
        }
    }

    private fun loadImage(link: String) {
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        val dispatcher =
            newSingleThreadContext("Network")
        if(checkImageExists(link)){
            Toast.makeText(requireContext() , checkImageExists(link).toString() , Toast.LENGTH_SHORT)
                .show()
            return
        }
        CoroutineScope(dispatcher).launch {
            val imageUrl = link
            try {
                val inputStream = URL(imageUrl).openStream()
                image = BitmapFactory.decodeStream(inputStream)
                handler.post {
                    binding.image.setImageBitmap(image)
                    binding.progressBar.isVisible = false
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext() , "Wrong link! Try another!" , Toast.LENGTH_SHORT)
                    .show()

            }
            dispatcher.close()
        }


    }
    private fun saveToGallery(
        context: Context ,
        bitmap: Bitmap ,
        albumName: String = "Tarkov_App"
    ) {
        val dispatcher =
            newSingleThreadContext("Disk")
        CoroutineScope(dispatcher).launch {
            val filename = "${System.currentTimeMillis()}.png"
            val write: (OutputStream) -> Boolean = {
                bitmap.compress(Bitmap.CompressFormat.PNG , 100 , it)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME , filename)
                    put(MediaStore.MediaColumns.MIME_TYPE , "image/png")
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH ,
                        "${Environment.DIRECTORY_DCIM}/$albumName"
                    )
                }

                context.contentResolver.let {
                    it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , contentValues)
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
                val image = File(imagesDir , filename)
                write(FileOutputStream(image))
            }
        }
        Toast.makeText(requireContext() , "Saved" , Toast.LENGTH_SHORT).show()
        dispatcher.close()
    }
    fun checkImageExists(urlString: String): Boolean {
        val url = URL(urlString)
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connectTimeout = 5000 // Время ожидания подключения
            connection.readTimeout = 5000 // Время ожидания ответа

            return connection.responseCode == HttpURLConnection.HTTP_OK
        }catch (e:Exception){
            return false
        }

    }

}






