package com.sachin.test.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sachin.test.databinding.RecItemBinding

class HomeAdapter(val imageClickListener:ListClickListener,val listName :List<HomePageData>) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: RecItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val binder = RecItemBinding.inflate(layoutInflater,parent,false)
        return ItemViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val imageObj = listName.get(position)
        val url = "https://picsum.photos/id/@id/200/300".replace("@id",imageObj.id)
        Glide
            .with(holder.binding.itemImageView.context)
            .load(url)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .into(holder.binding.itemImageView)
        holder.binding.itemImageView.setOnClickListener{imageClickListener.onImageClick(imageObj.id,holder.binding.itemImageView)}

    }

    override fun getItemCount(): Int {
        return listName.size
    }
    interface ListClickListener{
        fun onImageClick(id:String,v: View)
    }
}