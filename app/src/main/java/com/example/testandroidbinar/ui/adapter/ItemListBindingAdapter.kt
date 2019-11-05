package com.example.testandroidbinar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroidbinar.databinding.RvItemBinding
import com.example.testandroidbinar.model.Item

class ItemListBindingAdapter :ListAdapter<Item,ItemListBindingAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.nomor == newItem.nomor
        }
    }


    class ItemViewHolder (private var binding:RvItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind (item:Item){
            binding.item = item
            binding.executePendingBindings()
        }

    }

    internal fun getItemById(swipedPosition: Int): Item {
        return getItem(swipedPosition)
    }


}