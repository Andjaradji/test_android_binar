package com.example.testandroidbinar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testandroidbinar.db.ItemDatabase
import com.example.testandroidbinar.model.Item
import com.example.testandroidbinar.repo.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository

    val allItems: LiveData<List<Item>>

    init {
        val itemDao = ItemDatabase.getDatabase(application, viewModelScope).itemDao()
        repository = ItemRepository(itemDao)
        allItems = repository.allItems
    }

    fun insert(item: Item) = viewModelScope.launch {
        repository.insert(item)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}
