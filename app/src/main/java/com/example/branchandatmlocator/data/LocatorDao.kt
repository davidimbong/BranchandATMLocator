package com.example.branchandatmlocator.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.branchandatmlocator.model.Locations

@Dao
interface LocatorDao {

    @Query("SELECT * from locations_database")
    fun getLocations(): LiveData<List<Locations>>

    @Query("SELECT * from locations_database WHERE Name = :name")
    fun getLocation(name: String): Locations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<Locations>)

    @Query("DELETE from locations_database")
    fun deleteAll()
}

@Database(entities = [Locations::class], version = 1)
abstract class LocationsDatabase: RoomDatabase() {
    abstract fun locatorDao(): LocatorDao
}
