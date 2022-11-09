package com.example.branchandatmlocator.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.branchandatmlocator.data.LocationsDatabase
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.RequestBody
import com.example.branchandatmlocator.model.asDatabaseModel
import com.example.branchandatmlocator.model.asDomainModel
import com.example.branchandatmlocator.network.LocatorApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationsRepository(private val database: LocationsDatabase) {

    val locations: LiveData<List<Locations>> =
        Transformations.map(database.locatorDao.getLocations()) {
            it.asDatabaseModel()
        }

    suspend fun refreshRepository(list: List<Locations>) {
        withContext(Dispatchers.IO) {
            database.locatorDao.deleteAll()
            database.locatorDao.insertAll(list.asDatabaseModel())
        }
    }
}
