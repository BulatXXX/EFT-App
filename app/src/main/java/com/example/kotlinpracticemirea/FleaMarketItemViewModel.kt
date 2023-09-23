package com.example.kotlinpracticemirea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinpracticemirea.room.FleaMarketItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class FleaMarketItemViewModel @Inject constructor(private val repository: FleaMarketItemRepository) :
    ViewModel() {

    val items = repository.allItems.asLiveData()

    fun addItem(fleaMarketItem: FleaMarketItem){
        viewModelScope.plus(Dispatchers.IO).launch {
            repository.addItem(fleaMarketItem)
        }
    }

}