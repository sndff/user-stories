package com.saifer.storyapp.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saifer.storyapp.R
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.databinding.ItemStoryBinding

class ListStoryAdapter (private val listStory: List<ListStoryItem>): RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback : AdapterView.OnItemClickListener {
        fun onItemClicked(data: ListStoryItem?)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_item_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val img = listStory[position].photoUrl
        val name = listStory[position].name
        val desc = listStory[position].description
        Glide.with(holder.itemView.context)
            .load(img)
            .apply( RequestOptions().override(500,500))
            .into(holder.imgPhoto)
        holder.tvName.text = name
        holder.tvDesc.text = desc

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStory[holder.absoluteAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listStory.size

}