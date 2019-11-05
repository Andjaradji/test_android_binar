package com.example.testandroidbinar.repo

import androidx.lifecycle.LiveData
import com.example.testandroidbinar.db.ItemDao
import com.example.testandroidbinar.model.Item

class ItemRepository(private val itemDao: ItemDao) {

    val allItems: LiveData<List<Item>> = itemDao.getAllItems()

    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }

    suspend fun deleteAll(){
        itemDao.deleteAll()
    }

    suspend fun deleteItem(item: Item){
        itemDao.deleteItem(item)
    }


}