package com.example.branchandatmlocator.data

import android.content.Context
import androidx.room.*
import com.example.branchandatmlocator.model.Locations
import kotlinx.coroutines.flow.Flow

@Dao
interface LocatorDao {

//    @Query("SELECT * from branch_atm_info_database WHERE Name = :name")
//    fun getLocations(name: String): Flow<List<Locations>>

    @Query("SELECT * from branch_atm_info_database WHERE Name = :name")
    fun getLocations(name: String): Flow<List<Locations>>

    @Query("SELECT * from branch_atm_info_database WHERE BankId = :bankID")
    fun getLocation(bankID: String): Flow<Locations>
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
                "locations").build()
        }
    }
    return INSTANCE
}