package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from favoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM favoriteUser WHERE userId = :userId)")
    fun isFavorite(userId: String): LiveData<Boolean>
}