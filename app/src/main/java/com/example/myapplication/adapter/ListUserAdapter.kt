package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.database.UserItem
import com.example.myapplication.databinding.ItemUserBinding

class ListUserAdapter(
    private val listUser: List<UserItem>,
    private val listener: OnUserItemClick? = null
) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    interface OnUserItemClick {
        fun onUserItemClick(username: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bindItem(item: UserItem) {
            with(binding) {
                item.id.toString()
                tvUsername.text = item.login
                Glide.with(itemView)
                    .load(item.avatarUrl)
                    .into(ivProfilePicture)

                root.setOnClickListener {
                    listener?.onUserItemClick(item.login)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}