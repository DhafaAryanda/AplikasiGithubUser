package com.example.myapplication.database

import android.content.Context
import androidx.room.*

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserRoomDatabase::class.java, "favorite_user_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoomDatabase
        }
    }

}