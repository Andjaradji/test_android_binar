package com.example.testandroidbinar.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroidbinar.model.Item
import com.example.testandroidbinar.ui.adapter.ItemListBindingAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Item>?) {
    val adapter = recyclerView.adapter as ItemListBindingAdapter
    adapter.submitList(data)
}