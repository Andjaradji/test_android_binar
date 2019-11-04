package com.example.testandroidbinar.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "item_table")
@Parcelize
data class Item(
    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "tanggal")
    val tanggal: String,

    @ColumnInfo(name = "jumlah")
    val jumlah: String,

    @ColumnInfo(name = "pemasok")
    val pemasok: String

):Parcelable{
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nomor")
    var nomor:Int = 0
}