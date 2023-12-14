package com.example.sqlitepractice.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyTable::class], version = 1, exportSchema = false)
abstract class MyDaoDatabase : RoomDatabase() {
    abstract fun myDAO(): MyDAO

    companion object {
        @Volatile
        private var INSTANCE: MyDaoDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MyDaoDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDaoDatabase::class.java,
                    "my_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MyDaoDatabase
        }
    }
}
