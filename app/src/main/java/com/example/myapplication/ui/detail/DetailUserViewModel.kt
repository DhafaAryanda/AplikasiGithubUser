package com.example.myapplication.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.FavoriteUser
import com.example.myapplication.repository.FavoriteUserRepository

class DetailUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun isFavorite(userId: String): LiveData<Boolean> =
        mFavoriteUserRepository.isFavorite(userId)
}