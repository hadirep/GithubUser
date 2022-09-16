package com.example.submission3githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3githubuser.data.remote.ItemsItem
import com.example.submission3githubuser.databinding.ItemRowUserBinding

class ListAdapter(private val listUser: ArrayList<ItemsItem>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.user(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listUser[holder.layoutPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun user(item: ItemsItem) {
            binding.apply{
                tvLogin.text = item.login
                Glide.with(itemView.context)
                    .load(item.avatarUrl)
                    .circleCrop()
                    .into(ivAvatar)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}