package com.venkad.androidtest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.venkad.androidtest.data.network.model.Products

@Database(entities = arrayOf(Products::class), version = 1, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {

    abstract fun getProductsDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getDatabaseClient(context: Context): TestDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                        .databaseBuilder(context, TestDatabase::class.java, "TEST_DATABASE")
                        .fallbackToDestructiveMigration()
                        .build()

                return INSTANCE!!
            }
        }
    }
}