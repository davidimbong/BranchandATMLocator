package com.example.branchandatmlocator.di

import android.content.Context
import androidx.room.Room
import com.example.branchandatmlocator.data.LocationsDatabase
import com.example.branchandatmlocator.data.LocatorDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideLocatorDao(database: LocationsDatabase): LocatorDao {
        return database.locatorDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): LocationsDatabase {
        return Room.databaseBuilder(
            appContext,
            LocationsDatabase::class.java,
            "locations_database").build()
    }
}