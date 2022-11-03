package com.example.branchandatmlocator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.branchandatmlocator.model.Locations

@Database(entities = [Locations::class], version = 1, exportSchema = false)
abstract class LocatorDatabase : RoomDatabase() {
    abstract fun locatorDao(): LocatorDao

    companion object {
        @Volatile
        private var INSTANCE: LocatorDatabase? = null

        fun getDatabase(context: Context): LocatorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocatorDatabase::class.java,
                    "branch_atm_info_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}