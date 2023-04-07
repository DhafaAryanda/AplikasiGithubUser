package com.example.myapplication.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.FavoriteUser
import com.example.myapplication.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> =
        mFavoriteUserRepository.getAllFavoriteUser()
}