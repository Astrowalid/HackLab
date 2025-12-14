package com.example.hacklab.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hacklab.data.User
import com.example.hacklab.data.ProductCart
import com.example.hacklab.data.FavoriteProduct
import com.example.hacklab.data.dao.UserDao
import com.example.hacklab.data.dao.CartDao
import com.example.hacklab.data.dao.FavoriteDao

@Database(
    entities = [User::class, ProductCart::class, FavoriteProduct::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hacklab_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}