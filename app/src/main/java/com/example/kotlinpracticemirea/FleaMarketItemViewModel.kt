package com.example.kotlinpracticemirea

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

class FleaMarketItemViewModel @Inject constructor(private val fleaMarketDao: FleaMarketDao) :
    ViewModel() {

    val items = fleaMarketDao.getAllItems().asLiveData()

}