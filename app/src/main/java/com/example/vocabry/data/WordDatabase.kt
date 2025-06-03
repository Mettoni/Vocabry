package com.example.vocabry.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class],version = 1)
abstract class WordDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var Instance: WordDatabase? = null

        fun getDatabase(context:Context): WordDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WordDatabase::class.java,"WirdssDatabase.db").createFromAsset("WirdssDatabase.db").build().also{Instance=it}

            }
        }
    }
}