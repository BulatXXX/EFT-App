package com.example.kotlinpracticemirea.fragments

import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinpracticemirea.Item
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.databinding.FragmentItemBinding
import com.example.kotlinpracticemirea.databinding.FragmentSearchBinding
import com.example.retrofit.ItemApi
import com.example.retrofit.ItemInstance
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemBinding.inflate(layoutInflater , container , false)

        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        post("Makarov PM 9x18PM pistol")
    }

    private fun post(name: String){

        val paramObject = JSONObject()
        paramObject.put(
            "query" , "query {items(name:\"$name\"){id\n" +
                    "    name\n" +
                    "    description\n" +
                    "    avg24hPrice\n" +
                    "    height\n" +
                    "    width\n" +
                    "    iconLink\n" +
                    "    image512pxLink}}"
        )
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ItemInstance.ItemService.getItems(paramObject.toString())
                var responseBody = response.body().toString()

                Log.e("response" , responseBody)
                val itemsList = parseResponse(responseBody)
                binding.itemName.text = itemsList[0].name
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun parseResponse(responseBody: String):ArrayList<Item> {
        val first = responseBody.indexOf("[")
        val last = responseBody.indexOf("]")
        val stringItems = responseBody.substring(first+2,last-1).split("},{")
        val itemsList = ArrayList<Item>()
        for(item in stringItems){
            val name = item.substring(item.indexOf("\"name\":"),item.indexOf("\"description\":"))
            itemsList.add(Item(name))
        }
        Log.d("response",itemsList[0].name)

        return itemsList
    }


}