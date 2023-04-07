package com.example.myapplication.ui.favorite

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.database.FavoriteUser

class UserDiffCallback(private val mOldFavUserList: List<FavoriteUser>, private val mNewFavUserList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldFavUserList.size
    override fun getNewListSize(): Int = mNewFavUserList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavUserList[oldItemPosition].userId == mNewFavUserList[newItemPosition].userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldFavUserList[oldItemPosition]
        val newItem = mNewFavUserList[newItemPosition]
        return oldItem.username == newItem.username && oldItem.avatarUrl == newItem.avatarUrl
    }

}