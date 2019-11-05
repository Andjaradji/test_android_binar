package com.example.testandroidbinar.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testandroidbinar.model.Item


@Dao
interface ItemDao {

    @Query("SELECT * from item_table ORDER BY tanggal ASC")
    fun getAllItems():LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteItem(item:Item)
}