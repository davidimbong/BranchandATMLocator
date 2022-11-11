package com.example.branchandatmlocator.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.branchandatmlocator.model.Locations

@Dao
interface LocatorDao {

//    @Query("SELECT * from branch_atm_info_database WHERE Name = :name")
//    fun getLocations(name: String): Flow<List<Locations>>

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
    abstract val locatorDao: LocatorDao
}

private lateinit var INSTANCE: LocationsDatabase

fun getDatabase(context: Context): LocationsDatabase {
    synchronized(LocationsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                LocationsDatabase::class.java,
                "locations_database").build()
        }
    }
    return INSTANCE
}
//@Database(entities = [Locations::class], version = 1)
//abstract class LocationsDatabase: RoomDatabase() {
//    abstract val locatorDao: LocatorDao
//}
//
//private lateinit var INSTANCE: LocationsDatabase
//
//fun getDatabase(context: Context): LocationsDatabase {
//    synchronized(LocationsDatabase::class.java) {
//        if (!::INSTANCE.isInitialized) {
//            INSTANCE = Room.databaseBuilder(context.applicationContext,
//                LocationsDatabase::class.java,
//                "videos").build()
//        }
//    }
//    return INSTANCE
//}


