package com.example.branchandatmlocator.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.branchandatmlocator.data.LocationsDatabase
import com.example.branchandatmlocator.model.Locations
import com.example.branchandatmlocator.model.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationsRepository(private val database: LocationsDatabase) {

    val locations: LiveData<List<Locations>> =
        Transformations.map(database.locatorDao.getLocations()) {
            it.asDatabaseModel()
        }

    val location = MutableLiveData<Locations>()

    suspend fun refreshRepository(list: List<Locations>) {
        withContext(Dispatchers.IO) {
            database.locatorDao.deleteAll()
            database.locatorDao.insertAll(list.asDatabaseModel())
        }
    }

    suspend fun getLocation(name: String) {
        withContext(Dispatchers.IO) {
            location.postValue(database.locatorDao.getLocation(name))
        }
    }
}
