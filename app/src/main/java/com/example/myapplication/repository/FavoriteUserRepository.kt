package com.example.myapplication.repository

import android.app.Application
import com.example.myapplication.database.FavoriteUser
import com.example.myapplication.database.FavoriteUserDao
import com.example.myapplication.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUser: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUser = db.favoriteUserDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUser.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUser.delete(favoriteUser) }
    }

    fun getAllFavoriteUser() = mFavoriteUser.getAllFavoriteUser()

    fun isFavorite(userId: String) = mFavoriteUser.isFavorite(userId)
}