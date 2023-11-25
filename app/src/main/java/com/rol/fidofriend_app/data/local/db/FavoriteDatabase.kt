package com.rol.fidofriend_app.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rol.fidofriend_app.data.local.entity.FavoritePet

@Database(entities = [FavoritePet::class], version = 1)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {

        @Volatile
        private var instance: FavoriteDatabase? = null
        fun getDatabase(context: Context): FavoriteDatabase {
            val temInstance = instance
            if(temInstance !== null) {
                return temInstance
            }
            synchronized(this){
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favoriteDB"
                ).build()
                instance = _instance
                return _instance
            }
        }
    }
}