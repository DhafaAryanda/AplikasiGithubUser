package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.database.FavoriteUser
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.ui.favorite.UserDiffCallback

class ListFavoriteUserAdapter(
    private val listener: OnUserFavoriteClick? = null
) : RecyclerView.Adapter<ListFavoriteUserAdapter.ViewHolder>() {

    private val mListFavUser = ArrayList<FavoriteUser>()

    interface OnUserFavoriteClick {
        fun onUserFavoriteClick(username: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bindItem(item: FavoriteUser) {
            with(binding) {
                item.userId
                tvUsername.text = item.username
                Glide.with(itemView)
                    .load(item.avatarUrl)
                    .into(ivProfilePicture)

                root.setOnClickListener {
                    if (item.username != null) {
                        listener?.onUserFavoriteClick(item.username!!)
                    }
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
        holder.bindItem(mListFavUser[position])
    }

    override fun getItemCount(): Int = mListFavUser.size

    fun setListFavorite(listUser: List<FavoriteUser>) {
        val diffCallback = UserDiffCallback(mListFavUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mListFavUser.clear()
        mListFavUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }
}