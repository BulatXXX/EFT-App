package com.example.kotlinpracticemirea

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class FleaMarketItemViewModel @Inject constructor(private val repository: FleaMarketItemRepository) :
    ViewModel() {

    val items = repository.allItems.asLiveData()

}