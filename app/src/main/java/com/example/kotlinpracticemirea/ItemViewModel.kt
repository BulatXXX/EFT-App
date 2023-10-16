package com.example.kotlinpracticemirea

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinpracticemirea.room.FleaMarketItem
import com.example.kotlinpracticemirea.retrofit.ItemInstance
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) :
    ViewModel() {
    val foundItems = repository.foundItems
    private var searchJob: Job? = null
    private var favouriteJob: Job? = null
    val favouriteItems = repository.favouriteItems.asLiveData()
    var selectedItem = repository.selectedItem
    fun searchItem(name: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.plus(Dispatchers.IO).launch {
            delay(1500)

            repository.getItemsListFromApi(name)
        }
    }
    fun addToFavourites(item: Item){
        viewModelScope.plus(Dispatchers.IO).launch{
            repository.addItemToFavourites(item)
        }
    }
    fun deleteFromFavourites(item: Item){
        viewModelScope.plus(Dispatchers.IO).launch{
            repository.deleteItemFromFavourites(item)
        }
    }
    fun sendCheckIsFavourite(id: String){
        favouriteJob = viewModelScope.plus(Dispatchers.IO).launch(){
            repository.checkIsFavourite(id)
        }

    }






}